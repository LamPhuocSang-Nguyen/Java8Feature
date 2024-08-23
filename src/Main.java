import java.util.*;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;


public class Main {
    public static void test_stream_point() {
        List<Point> points = new ArrayList<>();
        points.add(new Point(7, 2));
        points.add(new Point(3, 4));
        points.add(new Point(2, 6));
        points.add(new Point(7, 1));

        //7+2+3+4+2+6+7+1
        int totalSum = points.stream()
                .flatMapToInt(point -> Arrays.stream(new int[]{point.getX(), point.getY()}))
                .sum();//tinh tong
        System.out.println("tong tat ca ca gia tri: "+ totalSum);

        //Tinh tong cac hoanh do > 5
        int sumX2 = points.stream()
                .filter(p -> p.getX() > 5)
                .mapToInt(Point::getX) //Chuyen doi thanh IntStream dua tren gia tri x
//                .mapToInt(p->{return p.getX();})
                .sum();
        System.out.println("Tong tat ca cac gia tri sumX2: "+ sumX2);

        //tinh tong toan bo x^2+1-y doi voi ca haoanh do > 5
        int sumX3 = points.stream()
                .filter(p -> p.getX() > 5)
                .mapToInt(p -> {
                            return p.getX() * p.getX() + 1 - p.getY();
                        }
                )
                .sum();
        System.out.println("Tong tat ca cac gia tri sumX3: "+ sumX3);


        //In ra tung ket qua x^2 + 1 - y doi voi x > 4
        points.stream()
                .filter(p->p.getX() > 4)
                .mapToInt(p->{
                    return p.getX() * p.getX() + 1 - p.getY();
                })
                //chuyen doi than Intstream dua tren gai tri x
                .forEach(res-> System.out.println(res));
        //kq =48 ,kq=49

        //Tinh tich toan bo x^2+1-y doi voi cac hoanh do > 1
        long total5 = points.stream()
                .filter(p -> p.getX() > 1)
                .mapToLong(p -> {
                            return p.getX() * p.getX() + 1 - p.getY();
                        }
                )
                .reduce(1l,(kq,item)->kq*item);
        System.out.println("total5 = "+ total5);


        //Tinh tich cac gia tri cua 1 / (x^2 + y) cho tat ca cac point
        double totalProduct = points.stream()
                .mapToDouble(point->{
                    int x = point.getX();
                    int y = point.getY();
                    int temp = x * x + y;
                    return 1.0/temp;
                })
                .reduce(1l, (kq, item)->kq * item); //tich tich toan bo cac gia tri

        System.out.println("Tinh tich toan bo gia tri cua 1 / (x^2 + y)" + totalProduct);

        //In ra danh sach x + y > 7
        points.stream()
                .filter(p->p.getX()+p.getY() > 7)
                .forEach(System.out::println);

        //In ra danh sach x>5 va y > 1
        points.stream()
                .filter(p->(p.getX() > 5 && p.getY() > 1))
                .forEach(System.out::println);

        //Tinh Tong cac x,y cua danh sahc
        List<Integer> danhSachTongx_y = points.stream()
                .map(p->{
                    return (p.getX() + p.getY());
                })
                .collect(Collectors.toList());

        System.out.println(danhSachTongx_y);
    }

    public static void main(String[] args) {
        ArrayList<String> colors = new ArrayList<>();
        colors.add("Red");
        colors.add("Green");
        colors.add("Blue");


//        for(String c : colors){
//            System.out.println(c);
//        }
        System.out.println(colors.get(1));


        //Thay doi phan tu tai vi tri chi dinh
        colors.set(1, "Yellow");

        //xoa phan tu theo gia tri
        colors.remove("red");



        //Tao mot mang
        String[] colorArray = {"Red", "Green", "Blue"};

        //tao array lis tu mang
        ArrayList<String> colorList = new ArrayList<>(Arrays.asList(colorArray));

        //Sap xep mang theo cach thuong
        Collections.sort(colorList);

        //Su dung bieu thuc lamda da sap xep danh sach theo thu tu tang dan
        Collections.sort(colorList, (s1, s2) -> s1.compareTo(s2));
        System.out.println(colorList); // {Blue, Green, Red}





        ArrayList<Point> points = new ArrayList<>();
        points.add(new Point(3,7));
        points.add(new Point(3,6));
        points.add(new Point(5,2));
        Comparator<Point> x_y_compare = new Comparator<Point>(){
//            @Override
//            public int compare(Point o1, Point o2){
//                return Integer.compare(o1.getX(), o2.getX());
//            }

            @Override
            public int compare(Point p1, Point p2){
                int tmp = Integer.compare(p1.getX(), p2.getX());
                if(tmp == 0){
                    return Integer.compare(p1.getY(), p2.getY());
                }
                return tmp;
            }

//            @Override
//            public int compare(Point p1, Point p2){
//                return Integer.compare(p1.getX() + p1.getY(), p2.getY() + p2.getY());
//            }
        };
        Collections.sort(points, x_y_compare);
        for(Point p : points){
            System.out.println(p);
        }


        System.out.println("########################");
        ArrayList<Point> pointsLst = new ArrayList<>();
        pointsLst.add(new Point(3,5));
        pointsLst.add(new Point(2,6));
        pointsLst.add(new Point(5,2));
        pointsLst.add(new Point(6,0));
        System.out.println("########################");
        System.out.println("Predicate");
        //predicate
        Predicate<Point> SumPoint = point -> point.getX() + point.getY() > 7;
        Predicate<Point> sumPoint2 = point ->{
            if(point.getX() > 5){
                return point.getX() + point.getY() > 7;
            }
            else{
                return point.getX() + point.getY() > 6;
            }
        };

        for (Point point : pointsLst){
            if(sumPoint2.test((point))){
                System.out.println(point.toString());
            }
        }

        System.out.println("Function");
        //function
        Function<Point, Integer> sumPoint3 = p ->p.getX() + p.getY();
        Function<Point, Integer> sumPoint4 = p ->{
            return p.getX() > p.getY() ? p.getY() - p.getX() : p.getY() + p.getX();
        };

        for(Point point : pointsLst){
            System.out.println(sumPoint4.apply(point));
        }

        System.out.println("consumer");
        //consumer
        Consumer<Point> printMessage = p ->{
            if(p.getX() > p.getY()){
                System.out.println(p.getY() - p.getX());
            }
        };

        for (Point point : pointsLst) {
            printMessage.accept(point);
        }


        //Stream
        System.out.println("Stream");
        pointsLst.stream()
                .filter(p ->p.getX() > 3)
                .forEach(System.out::println);
        System.out.println("Cac cach viet khac tuong tu Stream");
        for(Point p : pointsLst){
            if(p.getX() > 3){
                System.out.println(p.toString());
            }
        }
        Predicate<Point> pre_x = point -> point.getX() > 3;
        for(Point p : pointsLst){
            if (pre_x.test(p)){
                System.out.println(p.toString());
            }
        }
        Consumer<Point> consumer_x = p->{
            if(p.getX() > 3){
                System.out.println(p.toString());
            }
        };
        pointsLst.forEach(consumer_x);


        System.out.println("########################");
        System.out.println("Stream API");
        List<String> names = Arrays.asList("John", "Alice", "Bob", "Anna");

        //Loc cac ten bat dau bang 'A'
        names.stream()
                .filter(name -> name.startsWith("A"))
                .forEach(System.out::println); //output: Alice and Anna

        //Tuong tu
//        names.stream()
//                .filter(name -> name.startsWith("A"))
//                .forEach(item -> System.out.println(item));


        List<String> filteredNames = names.stream()
                .filter(name -> name.startsWith("A"))
                .map(String::toUpperCase)
                .sorted()
                .collect(Collectors.toList());

        System.out.println(filteredNames);// output [Alice, Anna]


        //Anh xa cac ten thanh chu hoa
        names.stream()
                .map(String::toUpperCase)
                .forEach(System.out::println);//output John, Alice, Bob

        names.stream()
                .sorted()
                .forEach(System.out::println); //output: Alice, Bob, John

        //Sap xep theo thu tu giam dan
        names.stream()
                .sorted(Comparator.reverseOrder())
                .forEach(System.out::println); //output John, Bob, Alice




        List<Integer> numbers = Arrays.asList(1,2,3,4,5);

        //Tinh Tong
        int sum = numbers.stream()
                .mapToInt(Integer::intValue)
                .sum();
        System.out.println("Sum: " + sum); //sum 15

        //Stream<Integer> => int
        //Tinh trung binh
        double average = numbers.stream()
                .mapToInt(Integer::intValue)
                .average()
                .orElse(0);
        System.out.println("Average: "+ average); // output Average: 3.0

        //Tim gia tri lon nhat
        OptionalInt max = numbers.stream()
                .mapToInt(Integer::intValue)
                .max();
        System.out.println("Max: " + (max.isPresent() ? max.getAsInt(): "Not present")); // output Max: 5

        //Tim gia tri nho nhat
        OptionalInt min = numbers.stream()
                .mapToInt(Integer::intValue)
                .min();
        System.out.println("Min: " + (min.isPresent() ? min.getAsInt(): "Not present")); // output min: 1

        //Su dung reduce de tinh tong
        int sum1 = numbers.stream()
                .reduce(0,Integer::sum);
        System.out.println("Sum using reduce: "+ sum1); // output sum using reduce: 15

        //su dung reduce de tinh tich
        int product = numbers.stream()
                .reduce(1,(a,b) -> a * b);
        System.out.println("Product using reduce: "+ product); // output product using reduce: 120

        List<List<String>> ListOfLists = Arrays.asList(
                Arrays.asList("a", "b", "c"),
                Arrays.asList("d", "e", "f"),
                Arrays.asList("g", "h", "i")
        );

        //Su dung flatMap de ket hop cac danh sach con thanh mot danh sach
        List<String> combinedList = ListOfLists.stream()
                .flatMap(List::stream)
                .collect(Collectors.toList());

        System.out.println(combinedList); //output [a,b,c,d,e,f,g,h,i]

        test_stream_point();
    }
}