package model;

import java.util.HashMap;
import java.util.Map;

public class QueryParameters extends HashMap<String, Object>{
    private QueryParameters(String name,Object value){
      put(name, value);
  }
  public static QueryParameters with(String name,Object value){
      return new QueryParameters(name, value);
  }
  public QueryParameters and(String name,Object value){
    this.
      put(name, value);
      return this;
  }
}

