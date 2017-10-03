package com.wuzp.newspace.utils.SetUtil;

import android.support.annotation.NonNull;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.Set;

/**
 * Created by wuzp on 2017/10/2.
 */
public class SetUtil {
    /**
     1.数据存储的"容器"：①数组 ②集合  ----保存在内存中的
     数组：int[] arr = new int[10]; arr[0] = 50;arr[1] = 50;...
     Object[] objs = new Object[10];
     集合： Vector v = new Vector(); v.addElement(Object obj);v.size();v.elementAt(0);

     数组的弊端：①数组的长度在进行初始化以后就确定了，意味着长度不可修改。
                 ②数组中真实存储的数据的个数没有现成的方法供调用获取。

     2.java集合框架
     Collection接口:操作一个一个的数据
     |------子接口：List接口：有序的、可重复的    ---"动态"数组  (比较常用的)
     |----ArrayList：是List的主要实现类，线程不安全；底层使用数组实现
     |----LinkedList：对于频繁的插入、删除操作，使用LinkedList效率高；底层使用链表实现
     |----Vector：是古老的实现类，线程安全的，效率低；底层使用数组实现
     |------子接口：Set接口：无序的、不可重复的   ---高中的"集合"
     |----实现类：HashSet/LinkedHashSet/TreeSet

     1.Map的框架
     |----Collection
     |----Map:操作的是一对一对的数据(key-value)
     |-----HashMap：主要的实现类，线程不安全的，可以存储null的key和null的value
     |------LinkedHashMap:是HashMap的子类，遍历时可以按照添加的顺序实现遍历，对于频繁的遍历，建议使用此类
     |-----TreeMap:按照添加的key-value的key的指定的属性进行排序。①自然排序②定制排序
     |-----Hashtable：古老的实现类，线程安全的，效率低，不可以存储null的key和null的value
     |------Properties：是Hashtable的子类，常用来处理属性文件。key和value都是String类型的
     2.（掌握）
     >Map中的元素是由key-value构成的。
     >Map中所有的key使用Set存放，无序的、不可重复的
     >Map中所有的value使用Collection存放的，无序的、可重复的
     >Map中的一个key-value构成一个Entry,所有的Entry使用Set存放的
     >Map中的键值对entry存放的依据：根据entry中的key的hash值决定。
     >Map中的key所在的类要求重写hashCode()和equals()方法
     Map中的value所在的类要求重写equals()方法
     * */

    //Map    Map接口 HashMap / LinkedHashMap/TreeMap/HashTable/Properties(HashTable的子类。key/value都是String)
    Map<String ,String> map = new Map<String, String>() {
        @Override
        public int size() {
            return 0;
        }

        @Override
        public boolean isEmpty() {
            return false;
        }

        @Override
        public boolean containsKey(Object key) {
            return false;
        }

        @Override
        public boolean containsValue(Object value) {
            return false;
        }

        @Override
        public String get(Object key) {
            return null;
        }

        @Override
        public String put(String key, String value) {
            return null;
        }

        @Override
        public String remove(Object key) {
            return null;
        }

        @Override
        public void putAll(@NonNull Map<? extends String, ? extends String> m) {

        }

        @Override
        public void clear() {

        }

        @NonNull
        @Override
        public Set<String> keySet() {
            return null;
        }

        @NonNull
        @Override
        public Collection<String> values() {
            return null;
        }

        @NonNull
        @Override
        public Set<Entry<String, String>> entrySet() {
            return null;
        }
    };

    //Collection 集合接口
    Collection<String> collection = new Collection<String>() {
        @Override
        public int size() {
            return 0;
        }

        @Override
        public boolean isEmpty() {
            return false;
        }

        @Override
        public boolean contains(Object o) {
            return false;
        }

        @NonNull
        @Override
        public Iterator<String> iterator() {
            return null;
        }

        @NonNull
        @Override
        public Object[] toArray() {
            return new Object[0];
        }

        @NonNull
        @Override
        public <T> T[] toArray(@NonNull T[] a) {
            return null;
        }

        @Override
        public boolean add(String s) {
            return false;
        }

        @Override
        public boolean remove(Object o) {
            return false;
        }

        @Override
        public boolean containsAll(@NonNull Collection<?> c) {
            return false;
        }

        @Override
        public boolean addAll(@NonNull Collection<? extends String> c) {
            return false;
        }

        @Override
        public boolean removeAll(@NonNull Collection<?> c) {
            return false;
        }

        @Override
        public boolean retainAll(@NonNull Collection<?> c) {
            return false;
        }

        @Override
        public void clear() {

        }
    };
    //集合的子类 list 接口   ArrayList  LinkedList Vector
    List<String> list = new List<String>() {
        @Override
        public int size() {
            return 0;
        }

        @Override
        public boolean isEmpty() {
            return false;
        }

        @Override
        public boolean contains(Object o) {
            return false;
        }

        @NonNull
        @Override
        public Iterator<String> iterator() {
            return null;
        }

        @NonNull
        @Override
        public Object[] toArray() {
            return new Object[0];
        }

        @NonNull
        @Override
        public <T> T[] toArray(@NonNull T[] a) {
            return null;
        }

        @Override
        public boolean add(String s) {
            return false;
        }

        @Override
        public boolean remove(Object o) {
            return false;
        }

        @Override
        public boolean containsAll(@NonNull Collection<?> c) {
            return false;
        }

        @Override
        public boolean addAll(@NonNull Collection<? extends String> c) {
            return false;
        }

        @Override
        public boolean addAll(int index, @NonNull Collection<? extends String> c) {
            return false;
        }

        @Override
        public boolean removeAll(@NonNull Collection<?> c) {
            return false;
        }

        @Override
        public boolean retainAll(@NonNull Collection<?> c) {
            return false;
        }

        @Override
        public void clear() {

        }

        @Override
        public String get(int index) {
            return null;
        }

        @Override
        public String set(int index, String element) {
            return null;
        }

        @Override
        public void add(int index, String element) {

        }

        @Override
        public String remove(int index) {
            return null;
        }

        @Override
        public int indexOf(Object o) {
            return 0;
        }

        @Override
        public int lastIndexOf(Object o) {
            return 0;
        }

        @Override
        public ListIterator<String> listIterator() {
            return null;
        }

        @NonNull
        @Override
        public ListIterator<String> listIterator(int index) {
            return null;
        }

        @NonNull
        @Override
        public List<String> subList(int fromIndex, int toIndex) {
            return null;
        }
    };
    //集合的子类 Set 接口  HashSet/LinkedSet/TreeSet
    Set<String> set = new Set<String>() {
        @Override
        public int size() {
            return 0;
        }

        @Override
        public boolean isEmpty() {
            return false;
        }

        @Override
        public boolean contains(Object o) {
            return false;
        }

        @NonNull
        @Override
        public Iterator<String> iterator() {
            return null;
        }

        @NonNull
        @Override
        public Object[] toArray() {
            return new Object[0];
        }

        @NonNull
        @Override
        public <T> T[] toArray(@NonNull T[] a) {
            return null;
        }

        @Override
        public boolean add(String s) {
            return false;
        }

        @Override
        public boolean remove(Object o) {
            return false;
        }

        @Override
        public boolean containsAll(@NonNull Collection<?> c) {
            return false;
        }

        @Override
        public boolean addAll(@NonNull Collection<? extends String> c) {
            return false;
        }

        @Override
        public boolean retainAll(@NonNull Collection<?> c) {
            return false;
        }

        @Override
        public boolean removeAll(@NonNull Collection<?> c) {
            return false;
        }

        @Override
        public void clear() {

        }
    };

    private ListView listView;
    class MyAdapter extends BaseAdapter{
        @Override
        public int getCount() {
            return 0;
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            return null;
        }
    }
}
