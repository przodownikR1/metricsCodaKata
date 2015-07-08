package pl.java.scalatech.web.controller;

import java.io.FileOutputStream;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.util.Queue;
import java.util.concurrent.Callable;
import java.util.concurrent.ConcurrentLinkedQueue;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.async.DeferredResult;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

@RestController
public class AsyncDemoController {

    private final Queue<DeferredResult<ModelAndView>> eventQueue = new ConcurrentLinkedQueue<>();

    @RequestMapping("/normal")
    public String normalCall() throws InterruptedException {
        Thread.sleep(9000);
        return "normal";
    }

    @RequestMapping("/async")
    public Callable<String> asyncCall(final Model model) {
        return new Callable<String>() {
            @Override
            public String call() throws Exception {
                Thread.sleep(9000);
                return "async";
            }
        };
    }

    @RequestMapping("/deferred")
    public DeferredResult<ModelAndView> deferredCall() {
        DeferredResult<ModelAndView> result = new DeferredResult<>();
        this.eventQueue.add(result);
        return result;
    }

    @Scheduled(fixedRate = 5000)
    public void simulateExternalThread() throws InterruptedException {
        Thread.sleep(9000);
        for (DeferredResult<ModelAndView> result : this.eventQueue) {
            result.setResult(new ModelAndView("deferred"));
            this.eventQueue.remove(result);
        }
    }

    @RequestMapping(value = "/uploadFile", method = RequestMethod.POST)
    public Callable<String> uploadFile(@RequestParam("file") final MultipartFile file) {
        return new Callable<String>() {
            @Override
            public String call() throws Exception {
                if (!file.isEmpty()) {
                    byte[] bytes = file.getBytes();
                    @SuppressWarnings("resource")
                    FileOutputStream fop = null;
                    try {
                        fop = new FileOutputStream(file.getOriginalFilename());
                        @SuppressWarnings("resource")
                        FileChannel out = fop.getChannel();
                        ByteBuffer buff = ByteBuffer.allocateDirect(bytes.length);
                        out.write(buff);
                        out.force(false);
                        buff.clear();
                        out.close();
                    } catch (Exception ex) {
                        throw new Exception(ex);
                    } finally {
                        if (fop != null)
                            fop.close();
                    }

                }
                return "success";
            }
        };
    }
}