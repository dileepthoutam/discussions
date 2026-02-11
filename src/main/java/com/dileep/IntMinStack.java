package com.dileep;

import java.util.Stack;

public class IntMinStack {
  private Stack<IntPair> stack;

  public IntMinStack() {
    this.stack = new Stack<>();
  }

  public void push(int val) {
    if (stack.isEmpty()) {
      stack.push(new IntPair(val, val));
      return;
    }
    IntPair top = stack.peek();
    stack.push(new IntPair(val, Math.min(val, top.minVal())));
  }


  public void pop() {
    stack.pop();
  }

  public int top() {
    return stack.peek().val();
  }

  public int getMin() {
    return stack.peek().minVal();
  }

  private static record IntPair(int val, int minVal) {}
}
