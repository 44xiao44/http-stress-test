
import java.util.concurrent.ConcurrentLinkedQueue;

public class HttpTest {


    private ConcurrentLinkedQueue<String>  concurrentLinkedQueue = new ConcurrentLinkedQueue<String>();
    private static int count_up = 0;
    private static int count_get = 0;
    private static boolean isRun = true;
    private static final int threadSize = 2;

    public static void main(String[] arg){

        for(int i = 0; i<threadSize ;i++){
            HttpTest httpTest = new HttpTest();
            httpTest.input();
            httpTest.output();
        }
    }

    private void input(){
            getInputThread().start();
    }

    private void output(){
            getOutputThread().start();
    }

    private Thread getInputThread(){

        return new Thread(new Runnable() {
            public void run() {

                while (isRun){

                    long start = System.currentTimeMillis();

                    String key = Test.getkey();

                    new PYDeepLink().callUp(key);

//                    inputList.add(key);
                    concurrentLinkedQueue.add(key);
                    count_up++;

                    if(count_up%1000 == 0){

                        System.out.println("up:" + count_up);
                        System.out.println("------------ 输入 : " + (System.currentTimeMillis() - start));

                    }
                }
            }
        });
    }

    private Thread getOutputThread(){

        return new Thread(new Runnable() {
            public void run() {
            while (isRun){

                long start = System.currentTimeMillis();

                String key = concurrentLinkedQueue.poll();
                if(null != key){
                    new PYDeepLink().callGet(key);
                    count_get++;
                    if(count_get%1000 == 0) {
                        System.out.println("gt : " + count_get);
                        System.out.println("------------ 获取 : " + (System.currentTimeMillis() - start));
                    }
                }

            }
            }
        });
    }
}
