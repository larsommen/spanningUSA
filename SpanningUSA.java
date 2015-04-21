public class SpanningUSA {

    private static ST<String, Integer> st;
    private static String[] keys;
    private static EdgeWeightedGraph ewg;

    /*
    *
    * Splits a string on '--', [ and ] (if they are present)
    */
    public static String [] splitter(String line){

        // remove '"' from the line    
        line=line.replace("\"","");

        // split the string if any of the splitters are present
        String [] split = line.split("--|\\[|\\]");

        for(int i=0;i< split.length;i++){
            split[i]=removeWhiteSpaceForAndAfter(split[i]);
        }
       
        return split;
    }

    /*
    *
    * If the split string has only one index - it is a vertex
    */
    public static boolean isVertex(String [] split){
        if(split.length==1){
            return true;
        }else
        {return false;}
    }

    /*
    *
    * Only removes white space in the beginning and at the end of a String
    */
    public static String removeWhiteSpaceForAndAfter(String s){
        while(s.charAt(0)==' '){
            s=s.substring(1, s.length());
        }
        while(s.charAt(s.length()-1)==' '){
            s=s.substring(0, s.length()-1);
        }
        return s;
    }

	public static void main(String[] args) {
        

        // to convert from index to string
        st = new ST<String, Integer>();

        // Reading words form piped file and puts them in st
        int index = 0;   

        // setup to chatch when there are no more vertecies (cities)
        boolean doneWithCities=false;

        while (!StdIn.isEmpty()) {   	
            String a = StdIn.readLine();

            String [] split=splitter(a);
            if(isVertex(split)){
                st.put(split[0], index);
                index ++;  

            }else{

                //this only runs once - first time we hit an edge
                if(!doneWithCities){
                    doneWithCities=true;
                    keys = new String[st.size()];
                    for (String name : st.keys()) {
                      keys[st.get(name)] = name;

                    }
                    ewg=new EdgeWeightedGraph(index);

                }
                int city1=st.get(split[0]);
                int city2=st.get(split[1]);
                double weight =Double.parseDouble((split[2]));

                Edge edge=new Edge(city1, city2, weight);
                ewg.addEdge(edge);

            }

        }
        LazyPrimMST lpMST = new LazyPrimMST(ewg);
        Iterable<Edge> listOfEdges = lpMST.edges();
        double lengthOflpMst=0;
        System.out.println("The MST has the following edges:");
        for(Edge edge: listOfEdges){
            System.out.println(keys[edge.either()]+" - "+keys[edge.other(edge.either())]+" weight: "+edge.weight());
            lengthOflpMst+=edge.weight();
        }
        System.out.println("Total lenght of MST: "+ lengthOflpMst);
    }


}