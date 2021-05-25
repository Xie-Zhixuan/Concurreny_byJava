public class initalVar {
    Season s=Season.get();
    point p=new point(0,0);
    public static void main(String[] args) {

        for (int i=0;i<10;i++){
//            initalVar oj=new initalVar();
//            System.out.println(oj.s);
            System.out.println(new initalVar().p==new initalVar().p);
        }
    }
    public record point(int x,int y){};

}
