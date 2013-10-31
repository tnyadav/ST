package com.tny.util.shorting;

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

/*public class MyComparator implements Comparator<Object>{

	
		@Override
		public int compare(Object lhs, Object rhs) {
			// TODO Auto-generated method stub
			 return ((Fruit) lhs).getFruitDesc().compareTo(((Fruit) rhs).getFruitDesc());
		}

	}*/
