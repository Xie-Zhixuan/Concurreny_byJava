import java.util.Random;

public enum Season {
    //可以为枚举类添加构造器、方法、字段：

    SPRING(1),SUMMER(2),AUTUMN(3),WINTER(4);
    private int order;

    public int getOrder() {
        return order;
    }

    private Season(int order){this.order=order;}
    //枚举的构造器总是私有的，可以省略。但绝不能声明为public或protected


    public static Season get(){
        int x= new Random().nextInt(4);
        return values()[x];
    }
}
