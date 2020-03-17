package com.li.yun.biao.mockito.test;

import com.li.yun.biao.mockito.dao.PersonDao;
import com.li.yun.biao.mockito.model.Person;
import com.li.yun.biao.mockito.service.PersonService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InOrder;

import java.util.LinkedList;
import java.util.List;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.eq;
import static org.mockito.Matchers.isA;
import static org.mockito.Mockito.*;


/**
 * @Author: liyunbiao
 * @Date: 2019/4/22 14:46
 */
public class PersonServiceTest {

    private PersonDao mockDao;
    private PersonService personService;

    @Before
    public void setUp() throws Exception {
        //模拟PersonDao对象
        mockDao = mock(PersonDao.class);
        when(mockDao.getPerson(1)).thenReturn(new Person(1, "Person1"));
        when(mockDao.update(isA(Person.class))).thenReturn(true);
        personService = new PersonService(mockDao);
    }

    @Test
    public void testUpdate() throws Exception {
        boolean result = personService.update(1, "new name");
        System.out.println(result);
        assertTrue("must true", result);
        //验证是否执行过一次getPerson(1)
        System.out.println(verify(mockDao, times(1)).getPerson(eq(1)));
        //验证是否执行过一次update
        System.out.println(verify(mockDao, times(1)).update(isA(Person.class)));
    }

    @Test
    public void testUpdateNotFind() throws Exception {
        boolean result = personService.update(2, "new name");
        System.out.println(result);
        assertFalse("must true", result);
        //验证是否执行过一次getPerson(1)
        verify(mockDao, times(1)).getPerson(eq(1));
        //验证是否执行过一次update
        verify(mockDao, never()).update(isA(Person.class));
    }

    @Test
    public void testUpdateList() throws Exception {
        anyInt();
        anyString();
        //TODO 模拟对象
        // 模拟LinkedList 的一个对象
        LinkedList mockedList = mock(LinkedList.class);
        // 此时调用get方法，会返回null，因为还没有对方法调用的返回值做模拟
        System.out.println(mockedList.get(0));
        //TODO 模拟方法调用的返回值
        // 模拟获取第一个元素时，返回字符串first。  给特定的方法调用返回固定值在官方说法中称为stub。
        when(mockedList.get(0)).thenReturn("first");
        // 此时打印输出first
        System.out.println(mockedList.get(0));
        System.out.println(mockedList.size());
        when(mockedList.size()).thenReturn(100);
        System.out.println(mockedList.size());
        //TODO 模拟方法调用抛出异常
        // 模拟获取第二个元素时，抛出RuntimeException
        //when(mockedList.get(1)).thenThrow(new NullPointerException());
        // 此时将会抛出RuntimeException
        System.out.println(mockedList.get(1));
        //TODO 如果一个函数没有返回值类型，那么可以使用此方法模拟异常抛出
        //doThrow(new RuntimeException("clear exception")).when(mockedList).clear();
        mockedList.clear();

        //TODO 模拟调用方法时的参数匹配
        // anyInt()匹配任何int参数，这意味着参数为任意值，其返回值均是element
        when(mockedList.get(anyInt())).thenReturn("element");
        // 此时打印是element
        System.out.println(mockedList.get(999));
        //TODO 模拟方法调用次数
        // 调用add一次
        mockedList.add("once");
        // 下面两个写法验证效果一样，均验证add方法是否被调用了一次
        System.out.println( verify(mockedList).add("once"));
        System.out.println( verify(mockedList, times(1)).add("once"));

        // TODO校验行为
        // mock creation
        List mockedList1 = mock(List.class);
        // using mock object
        mockedList1.add("one");
        mockedList1.clear();
        //verification
        System.out.println(verify(mockedList1).add("one"));
        verify(mockedList1).clear();
    }

    @Test
    public void Stubbing(){
        //You can mock concrete classes, not just interfaces
        LinkedList mockedList = mock(LinkedList.class);
        //stubbing
        when(mockedList.get(0)).thenReturn("first");
        //when(mockedList.get(1)).thenThrow(new RuntimeException());
        //following prints "first"
        System.out.println(mockedList.get(0));
        //following throws runtime exception
        System.out.println(mockedList.get(1));
        //following prints "null" because get(999) was not stubbed
        System.out.println(mockedList.get(999));

        System.out.println(verify(mockedList).get(0));

        List list = new LinkedList();
        List spy = spy(list);
        //optionally, you can stub out some methods:
        when(spy.size()).thenReturn(100);
        //using the spy calls *real* methods
        spy.add("one");
        spy.add("two");
        //prints "one" - the first element of a list
        System.out.println(spy.get(0));
        //size() method was stubbed - 100 is printed
        System.out.println(spy.size());
        //optionally, you can verify
        verify(spy).add("one");
        verify(spy).add("two");
    }

    @Test
    public void testVerificationInOrder() throws Exception {
        // A. Single mock whose methods must be invoked in a particular order
        List singleMock = mock(List.class);

        //使用单个mock对象
        singleMock.add("was added first");
        singleMock.add("was added second");

        //创建inOrder
        InOrder inOrder = inOrder(singleMock);

        //验证调用次数，若是调换两句，将会出错，因为singleMock.add("was added first")是先调用的
        inOrder.verify(singleMock).add("was added first");
        inOrder.verify(singleMock).add("was added second");

        // 多个mock对象
        List firstMock = mock(List.class);
        List secondMock = mock(List.class);

        //using mocks
        firstMock.add("was called first");
        secondMock.add("was called second");

        //创建多个mock对象的inOrder
        inOrder = inOrder(firstMock, secondMock);

        //验证firstMock先于secondMock调用
        inOrder.verify(firstMock).add("was called first");
        inOrder.verify(secondMock).add("was called second");
    }
}
