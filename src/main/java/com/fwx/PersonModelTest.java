package com.fwx;

import com.google.protobuf.InvalidProtocolBufferException;
import com.google.protobuf.util.JsonFormat;
import org.junit.Test;
/**
 * @author Fwx
 * @date 2020/2/13 20:59
 * @param null
 * @return
*/
public class PersonModelTest {
    @Test
    public void printObject() {
        PersonModel.Person.Builder personBuilder = PersonModel.Person.newBuilder();
        personBuilder.setId(1);
        personBuilder.setName("TestName");
        personBuilder.setAge(18);

        //声明一个Person对象
        PersonModel.Person person = null;

        //********** 1.输出对象内容
        //builder和builder.build()输出内容:
        //id: 1
        //name: "TestName"
        //age: 18
        System.out.println("1.输出对象内容：");
        System.out.println(personBuilder);

        //初始化person对象
        person = personBuilder.build();
        System.out.println(person);

        //********** 2.输出对象字节内容
        System.out.println("2.输出对象字节内容：");
        byte[] personBytes = personBuilder.build().toByteArray();
        for (byte b : personBytes) {
            System.out.print(b);
        }
        System.out.println("");
        System.out.println("");

        //********** 3.将person字节数组转化回person对象
        try {
            PersonModel.Person personD = PersonModel.Person.parseFrom(personBytes);
            System.out.println("3.将person字节数组转化回person对象:");
            System.out.println(personD);
        } catch (InvalidProtocolBufferException e) {
            e.printStackTrace();
        }

        //********** 4.将builder转为json对象
        String str = "";
        try {
            PersonModel.Person person1 = PersonModel.Person.parseFrom(personBytes);
            //创建printer对象
            JsonFormat.Printer printer = JsonFormat.printer();
            System.out.println("4.将builder转为json对象:");
            str = printer.print(person1);
            System.out.println(str);
        } catch (InvalidProtocolBufferException e) {
            e.printStackTrace();
        }

        //********** 5.将json对象转为builder
        PersonModel.Person.Builder person2 = PersonModel.Person.newBuilder();
        JsonFormat.Parser parser = JsonFormat.parser();
        try {
            parser.merge(str, person2);
            System.out.println("5.将json对象转为builder:");
            System.out.println(person2);
        } catch (InvalidProtocolBufferException e) {
            e.printStackTrace();
        }

        //********** 6.proto相关操作（在builder对象中set*方法设置值）
        PersonModel.Person.Builder builder = PersonModel.Person.newBuilder();
        builder.setId(2);
        builder.setName("test_proto2");
        builder.setAge(19);

        PersonModel.Person person1 = builder.build();

        System.out.println("6.proto相关操作:");
        boolean idflag = person1.hasId();
        System.out.println("是否有id：" + idflag);
        System.out.println("对象值：" + person1);

        //对象转builder
        PersonModel.Person.Builder builder1 = person1.toBuilder();
        //builder转对象
        PersonModel.Person person3 = builder1.build();
        //builer序列化
        byte[] bytes = person3.toByteArray();
        //反序列化
        try {
            PersonModel.Person person4 = PersonModel.Person.parseFrom(bytes);
            System.out.println("person4"+person4);
        } catch (InvalidProtocolBufferException e) {
            e.printStackTrace();
        }
    }
}
