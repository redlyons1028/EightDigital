/**
 * 
 */
package com.red.test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author Administrator
 *
 */
public class EightCode {

	/**
	 * 构造函数
	 */
	public EightCode() {

	}

	public EightCode(int[] num) {
		this.num = num;
		this.floor = 1;
		initMoveChart();
		this.position = searchzero();
	}

	// 数组中间态
	private int[] num;
	// 0的位置
	private int position;
	// 父节点
	private EightCode father;
	// 行动chart
	private List<Integer> move_chart;
	
	// 层数
	private int floor;
	
	public int getFloor() {
		return floor;
	}

	public void setFloor(int floor) {
		this.floor = floor;
	}

	public int[] getNum() {
		return num;
	}

	public void setNum(int[] num) {
		this.num = num;
	}

	public int getPosition() {
		return position;
	}

	public void setPosition(int position) {
		this.position = position;
	}

	public EightCode getFather() {
		return father;
	}

	public void setFather(EightCode father) {
		this.father = father;
		EightCode fatherKun = this.getFather();
		if (fatherKun != null) {
			int fatherZero = fatherKun.getPosition();
			int result = fatherZero - getPosition();
			if (result == 1) {
				this.move_chart.remove(Integer.valueOf(3));
			} else if (result == -1) {
				this.move_chart.remove(Integer.valueOf(2));
			} else if (result == 3) {
				this.move_chart.remove(Integer.valueOf(1));
			} else if (result == -3) {
				this.move_chart.remove(Integer.valueOf(0));
			}
		}
	}

	public List<Integer> getMove_chart() {
		return move_chart;
	}

	public void setMove_chart(List<Integer> move_chart) {
		this.move_chart = move_chart;
	}

	public void printNum() {
		int j = 1;
		for (int i = 0; i < 9; i++) {
			if (j > 3) {
				j = 1;
				System.out.println();
			}
			System.out.print(this.getNum()[i] + " ");
			j++;
		}
	}

	public int searchzero() {
		for (int i = 0; i < 9; i++) {
			if (this.getNum()[i] == 0)
				return i;
		}
		return 0;
	}

	public int[] swapPos(int a, int b) {
		int[] group;
		group = Arrays.copyOf(num, num.length);
		int temp = group[a];
		group[a] = group[b];
		group[b] = temp;
		return group;
	}

	public EightCode moveUp() {
		EightCode ec = new EightCode(swapPos(position, position - 3));
		ec.setFather(this);
		return ec;
	}

	public EightCode moveDown() {
		EightCode ec = new EightCode(swapPos(position, position + 3));
		ec.setFather(this);
		return ec;
	}

	public EightCode moveLeft() {
		EightCode ec = new EightCode(swapPos(position, position - 1));
		ec.setFather(this);
		return ec;
	}

	public EightCode moveRight() {
		EightCode ec = new EightCode(swapPos(position, position + 1));
		ec.setFather(this);
		return ec;
	}

	/**
	 * 0:up 1:down 2:left 3:right
	 * 
	 * @param move
	 * @return
	 */
	public EightCode moving(int move) {
		EightCode son = new EightCode();
		switch (move) {
		case 0:
			son = this.moveUp();
			break;
		case 1:
			son = this.moveDown();
			break;
		case 2:
			son = this.moveLeft();
			break;
		case 3:
			son = this.moveRight();
			break;
		default:
			break;
		}
		return son;
	}

	/**
	 * 
	 * @return 小于3的不能上移返回false
	 */
	public boolean isMoveUp() {
		int position = searchzero();
		if (position <= 2) {
			return false;
		}
		return true;
	}

	/**
	 * 
	 * @return 大于6返回false
	 */
	public boolean isMoveDown() {
		int position = searchzero();
		if (position >= 6) {
			return false;
		}
		return true;
	}

	/**
	 * 
	 * @return 0，3，6返回false
	 */
	public boolean isMoveLeft() {
		int position = searchzero();
		if (position % 3 == 0) {
			return false;
		}
		return true;
	}

	/**
	 * 
	 * @return 2，5，8不能右移返回false
	 */
	public boolean isMoveRight() {
		int position = searchzero();
		if ((position) % 3 == 2) {
			return false;
		}
		return true;
	}

	public void initMoveChart() {
		this.move_chart = new ArrayList<>();
		if (isMoveUp()) {
			this.move_chart.add(0);
		}
		if (isMoveDown()) {
			this.move_chart.add(1);
		}
		if (isMoveLeft()) {
			this.move_chart.add(2);
		}
		if (isMoveRight()) {
			this.move_chart.add(3);
		}
	}

	public boolean isEquals(EightCode end) {
		for (int i = 0; i < 9; i++) {
			if (this.getNum()[i] != end.getNum()[i])
				return false;
		}
		return true;
	}



	
	
}
