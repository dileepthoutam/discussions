package com.dileep;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Stack;

public class JsonParser {
  private HashMap<String, Object> keyValueMap;
  private Stack<Character> stack;

  public JsonParser() {
    this.keyValueMap = new HashMap<>();
    this.stack = new Stack<>();
  }

  public void printFile(String filename) {
    try (BufferedInputStream bufferedInputStream = new BufferedInputStream(
        JsonParser.class.getClassLoader().getResourceAsStream(filename)
    )) {
      while (bufferedInputStream.available() > 0) {
        char c = (char) bufferedInputStream.read();
        System.out.print(c);
      }
    } catch (IOException e) {
      e.printStackTrace();
    }
    System.out.println();
    System.out.println();
  }

  public void parse(String filename) {
    try (BufferedInputStream bufferedInputStream = new BufferedInputStream(
        JsonParser.class.getClassLoader().getResourceAsStream(filename)
    )) {
      boolean isValue = false;
      boolean canIncludeSpace = false;
      char ch = (char) bufferedInputStream.read();
      if (ch != '{') {
        throw new RuntimeException("Invalid Json.");
      }
      StringBuilder key = new StringBuilder();
      StringBuilder value = new StringBuilder();
      while (bufferedInputStream.available() > 0) {
        ch = (char) bufferedInputStream.read();
        if ((!canIncludeSpace) && (ch == ' ' || ch == '\n' || ch == '\t')) {
          continue;
        } else if (ch == '"') {
          if (!stack.isEmpty() && stack.peek() == ch) {
            stack.pop();
            if (!isValue && !key.isEmpty()) {
              keyValueMap.put(key.toString(), null);
            } else {
              keyValueMap.put(key.toString(), value.toString());
              key = new StringBuilder();
              value = new StringBuilder();
              canIncludeSpace = false;
            }
          } else {
            stack.push(ch);
            if (isValue) {
              canIncludeSpace = true;
            }
          }
        } else if(ch == ':') {
          isValue = true;
        } else if (ch == ',') {
          isValue = false;
        } else {
          if (!isValue) {
            key.append(ch);
          } else {
            value.append(ch);
          }
        }
      }
    } catch (IOException e) {
      e.printStackTrace();
    }

  }

  public HashMap<String, Object> getKeyValueMap() {
    return keyValueMap;
  }

  public Stack<Character> getStack() {
    return stack;
  }
}
