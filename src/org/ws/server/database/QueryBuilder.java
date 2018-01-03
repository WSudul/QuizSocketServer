package org.ws.server.database;


import java.util.*;


public class QueryBuilder {


    private final String select="SELECT";
    private final String from="FROM";
    private final String where="WHERE";
    private final String join="JOIN";
    private final String on="ON";
    private final String inner="INNER";
    private final String left="LEFT";
    private final String right="RIGHT";
    private final String like="LIKE";
    private final String in = "IN";
    private final String not = "NOT";

    private StringBuilder stringBuilder;

    private List<String> selectedColumns;
    private List<String> fromTables;

    private String queryString;

    public QueryBuilder(){
        stringBuilder=new StringBuilder(64);
    }

    public QueryBuilder(Integer bufferSize){
        stringBuilder=new StringBuilder(bufferSize);
    }

    String BuildQuery(){
        stringBuilder.append(";");
        return stringBuilder.toString();
    }


    public QueryBuilder select(){
        stringBuilder.append(select);
        return this;
    }

    public QueryBuilder columns(List<String> columns){
        stringBuilder.append(columns);
        return this;
    }

    public QueryBuilder from(List<String> tables){
        this.fromTables=tables;
        stringBuilder.append(from);
        stringBuilder.append(tables);
        return this;
    }

    public QueryBuilder where(String condition){
        stringBuilder.append(where);
        stringBuilder.append(condition);
        return this;
    }

    public QueryBuilder join(JoinType type,String table,String condition){

        String joinTypeString;
        switch(type){
            case INNER:
                joinTypeString=inner;
                break;
            case LEFT:
                joinTypeString=left;
                break;
            case RIGHT:
                joinTypeString=right;
                break;
            default:
                joinTypeString="";
                break;
        }


        stringBuilder.append(joinTypeString);
        stringBuilder.append(table);
        stringBuilder.append(on);
        stringBuilder.append(condition);

        return this;

    }

}
