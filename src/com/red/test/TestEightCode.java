/**
 * 
 */
package com.red.test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Administrator
 *
 */
public class TestEightCode {

	/**
	 * @param args
	 */

	private List<EightCode> ecList;

	private int[] start = { 7, 2, 4, 5, 0, 6, 8, 3, 1 };

	private int[] end = { 0, 1, 2, 3, 4, 5, 6, 7, 8 };

	private Map<Integer, List<EightCode>> aMap;

	private List<Integer> midList;

	// 开始态
	private EightCode ecStart;

	// 终结态
	private EightCode ecEnd;

	private int dai = 6;

	public EightCode getEcStart() {
		return ecStart;
	}

	public void setEcStart(EightCode ecStart) {
		this.ecStart = ecStart;
	}

	public EightCode getEcEnd() {
		return ecEnd;
	}

	public void setEcEnd(EightCode ecEnd) {
		this.ecEnd = ecEnd;
	}

	public List<EightCode> getEcList() {
		return ecList;
	}

	public void setEcList(List<EightCode> ecList) {
		this.ecList = ecList;
	}

	public int[] getStart() {
		return start;
	}

	public void setStart(int[] start) {
		this.start = start;
	}

	public int[] getEnd() {
		return end;
	}

	public void setEnd(int[] end) {
		this.end = end;
	}

	public void initParam() {
		// 状态存储表
		this.ecList = new ArrayList<>();
		this.ecStart = new EightCode(this.getStart());
		this.ecEnd = new EightCode(this.getEnd());

		aMap = new HashMap<>();

		midList = new ArrayList<>();
	}

	public static void main(String[] args) {
		// new TestEightCode().calculate();
		new TestEightCode().a_plus();
	}

	public void a_plus() {
		initParam();
		// this.ecList.add(this.ecStart);
		this.go(this.ecStart);
	}

	public void circlePrint(EightCode e) {
		if (e.isEquals(ecStart)) {
			ecList.add(e);
			Collections.reverse(ecList);
			for (int i = 0; i < ecList.size(); i++) {
				ecList.get(i).printNum();
				System.out.println("");
				System.out.println("--------");
			}
		} else {
			EightCode father = e.getFather();
			ecList.add(e);
			circlePrint(father);
		}
	}

	public void go(EightCode e) {
		EightCode midStatus = e;
		
		for (int j = 0;j<1000000000;j++) {
			if (midStatus.isEquals(ecEnd)) {
				circlePrint(midStatus);
				System.out.println("success!" + (ecList.size()-1) + " steps at all!");
				break;
			} else {
				int floor = midStatus.getFloor();
				List<Integer> steps = midStatus.getMove_chart();
				for (int i = 0; i < steps.size(); i++) {
					EightCode single = midStatus.moving(steps.get(i));
					single.setFather(midStatus);
					single.setFloor(floor + 1);
					int weight = this.calDiffer(single) + floor;
					if (aMap.get(weight) == null || aMap.get(weight).size() == 0) {
						List<EightCode> a = new ArrayList<>();
						a.add(single);
						aMap.put(weight, a);
					} else {
						List<EightCode> b = aMap.get(weight);
						b.add(single);
						aMap.put(weight, b);
					}
					midList.add(weight);

				}
				Collections.sort(midList);
				int next = midList.get(0);
				midList.remove(0);
				EightCode c = aMap.get(next).get(aMap.get(next).size() - 1);
				aMap.get(next).remove(aMap.get(next).size() - 1);
				midStatus = c;
			}
		}
	}

	public int calDiffer(EightCode e) {
		int rel = 0;
		int[] a = e.getNum();
		for (int i = 0; i < end.length; i++) {
			if (a[i] != 0 && a[i] != end[i]) {
				rel = rel + 1;
			}
		}

		return rel;
	}

	public void calculate() {
		initParam();
		this.ecList.add(this.ecStart);
		this.walk(this.ecStart);
	}

	public void walk(EightCode e) {
		if (e.getMove_chart().size() == 0) {
			if (e.getFather() == null) {
				System.out.println("无解了");
			} else {
				EightCode eFather = e.getFather();
				eFather.getMove_chart().remove(0);
				ecList.remove(e);
				walk(eFather);
			}
		} else {
			EightCode eSon = new EightCode();
			eSon = e.moving(e.getMove_chart().get(0));
			// e是第dai-1代
			if (ecList.size() == this.dai - 1) {
				if (eSon.isEquals(ecEnd)) {
					ecList.add(eSon);
					for (int i = 0; i < ecList.size(); i++) {
						ecList.get(i).printNum();
						System.out.println("");
						System.out.println("--------");
					}
				} else {
					e.getMove_chart().remove(0);
					walk(e);
				}
			} else {// e不是第dai-1代
				if (eSon.isEquals(ecEnd)) {
					ecList.add(eSon);
					for (int i = 0; i < ecList.size(); i++) {
						ecList.get(i).printNum();
						System.out.println("");
						System.out.println("--------");
					}
				} else {
					ecList.add(eSon);
					walk(eSon);
				}
			}
		}
	}

}
