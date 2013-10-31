package com.example.tnutil;

import java.util.Comparator;

public class MyComparator implements Comparator<Object>{
 MyCallback myCallback;
	public MyComparator( MyCallback myCallback)
	{
		this.myCallback=myCallback;
	}
	@Override
	public int compare(Object o1, Object o2) {
		// TODO Auto-generated method stub
		return myCallback.run( o1,  o2);
	}
	

	

}
interface MyCallback {
	public int run(Object o1, Object o2) ;
	}

/*Collections.sort(arrayList,new MyComparator(new MyCallback() {
	
	@Override
	public int run(Object o1, Object o2) {
		// TODO Auto-generated method stub
		Fruit f1=(Fruit) o1;
		Fruit f2=(Fruit) o2;
		
		 return f1.getFruitDesc().compareTo(f2.getFruitDesc());
	}
}));*/