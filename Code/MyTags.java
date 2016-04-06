import java.util.ArrayList;
public class MyTags {


public static boolean findTag(String term) {
    boolean result;
    int row = tags.length;

    outer_loop: 
    for (int i=0; i<tags.length; i++) {
        if (term.equals(tags[i][0])) {
            row = i;
            break outer_loop;
        }
        
    }

    if (row == tags.length) {
        result =false;
    }
    else {
        result =true;
        }

    return result;
}


public static boolean findAttr(String term) {
    boolean result;
    int row = tags.length;

    outer_loop: 
    for (int i=0; i<tags.length; i++) {
        for (int j=1; j<7; j++) {
            if (term.equals(tags[i][j])) {
                 row = i;
                 break outer_loop;
            }
        }
    }

    if (row == tags.length) {
        result =false;
    }
    else {
        result =true;
        }

    return result;
}

public static int findParentPos(String parent){
    int row = tags.length;
    int pos=0;

    outer_loop: 
    for (int i=0; i<tags.length; i++) {
        if (parent.equals(tags[i][0])) {
            pos=i;
            break outer_loop;
        }
    }

    return pos;
}

public static String CorrectIt(String passed_){
   
   boolean result;
   int row = tags.length;
   String corrected="";
   char[] passed = passed_.toCharArray();
   int max=0;
   int posOfMax=0 ;
   
   Correction elem =null;
   ArrayList<Correction> a =new ArrayList<Correction>();
   ArrayList<Correction> b =new ArrayList<Correction>();
   ArrayList<Correction> c =new ArrayList<Correction>();
   
   for (int i=0; i<tags.length; i++) {
         int counter=0;
         String actual_ = tags[i][0];
         char[] actual= actual_.toCharArray();
         for (int k=0; k<actual.length; k++) {
            for (int l=0; l<passed.length; l++) {
               if(passed[l]==actual[k]) {
                  counter++;
               }
            }
         }
         
         
         elem = new Correction(i, actual_, counter, actual_.length(),false);
         a.add(elem);
         //System.out.println(a.get(i).getTotal_matches()+ " " +a.get(i).getTag_name()+ " "+a.get(i).getLength()+ " "+a.get(i).getSuggested());

   }
   
   for (int m=0; m<a.size(); m++) {
      if(a.get(m).getTotal_matches() >= max) {
		max= a.get(m).getTotal_matches();
        elem = new Correction(m,a.get(m).getTag_name(),a.get(m).getTotal_matches() ,a.get(m).getLength(),true);
        b.add(elem); 
         }
   }
   for (int n=0; n<b.size(); n++) {
		if(b.get(n).getTotal_matches() == max) {
			elem = new Correction(n,b.get(n).getTag_name(), b.get(n).getTotal_matches(), b.get(n).getLength(),false);
			c.add(elem);
			//System.out.println(b.get(n).getTotal_matches()+ " " +b.get(n).getTag_name()+ " "+b.get(n).getLength()+ " "+b.get(n).getSuggested());
			
		}
     } 
	if (c.size()==1){
		corrected = c.get(0).getTag_name();
	}
	else {
		for (int z=0; z<c.size(); z++) {
			if(c.get(z).getTotal_matches() == max) {
				if (c.get(z).getLength() == passed_.length() ) {
					corrected = c.get(z).getTag_name();
				}
			}
		}
	}
        
        if (corrected == ""){
			for (int i=0; i<tags.length; i++) {
				int counter=0;
				String actual_ = tags[i][0];
				if (actual_.length() >= passed_.length()) {
					for (int j = 0; j <= (actual_.length() - passed_.length()); j++) {
						if (actual_.regionMatches(j, passed_, 0, passed_.length())) {
							corrected = actual_ ;
						}
					}
				}
			}
		}
	 	
   return corrected;

}

static String[][] tags = new String [][] { 
{ "html", "lang","", "", "1",  "", "P" },
{ "head", "","", "", "2",  "", "P" },
{ "title", "","", "", "3",  "", "P" },
{ "style", "media","scoped", "type", "4",  "", "P" },
{ "body", "background","bgcolor", "", "5",  "", "P" },
{ "h1", "title","", "", "6",  "", "UP" },
{ "h2", "title","", "", "6",  "", "UP" },
{ "h3", "title","", "", "6",  "", "UP" },
{ "h4", "title","", "", "6",  "", "UP" },
{ "h5", "title","", "", "6",  "", "UP" },
{ "h6", "title","", "", "6",  "", "UP" },
{ "marquee", "behavior","id", "loop", "7",  "", "P" },
{ "pre", "","", "", "8",  "", "P" },
{ "br", "","", "", "9",  "", "UP" },
{ "em", "","", "", "10",  "", "P" },
{ "center", "","", "", "11",  "", "P" },
{ "img", "src","width", "height", "12",  "", "UP" },
{ "form", "action","method", "accept", "13",  "", "P" },
{ "input", "type","name", "value", "placeholder" ,"14",  "", "P" },
{ "select", "name","autofocus", "size", "15",  "", "P" },
{ "option", "value","selected", "disabled", "16",  "", "P" },
{ "table", "border","border-color", "cellspacing", "cellpadding", "rules", "17",  "", "P" },
{ "tr", "border","align", "bgcolor", "18",  "", "P" },
{ "td", "border","align", "bgcolor", "19",  "", "P" },
{ "ol", "start","", "reversed", "20",  "", "P" },
{ "li", "value","", "", "21",  "", "P" },
{ "a", "href","rel", "target", "color", "face",  "22", "P" },
{ "menu", "type","contextmenu", "", "23",  "", "P" },
{ "menuitem", "","", "", "24",  "", "P" },
{ "frameset", "cols","rows", "target", "25",  "", "P" },
{ "div", "class","height", "width","role", "26",  "", "P" },
{ "caption", "align","", "", "27",  "", "P" },
{ "area", "target","alt", "media", "28",  "", "P" },
{ "audio", "src","controls", "loop", "29",  "", "P" },
{ "strike", "","", "", "30",  "", "P" },
{ "th", "border","align", "colspan", "31",  "", "P" },
{ "meta", "name", "width", "initial-scale", "maximum-scale","content","charset", "", "UP" },
{ "link", "href", "rel", "type", "media" ,  "", "UP" } ,
{ "script", "src","type", "",  "", "",  "", "P" },
{ "ul", "class","", "",  "",  "", "P" },
{ "p" ,"" , "","", "","",  "", "P" },
{ "i", "","", "","",  "", "P" },
{ "span", "class","", "","", "", "P" },
{ "button", "type", "class", "data-toggle", "data-target",  "", "P" },
{ "header", "","", "","",  "", "P" },
{ "footer", "","", "","",  "", "P" },
{ "nav", "","", "","",  "", "P" },
{ "font", "face", "font size", "color", "" ,"" ,"P"}
};
}

class Correction {
    int pos,total_matches,length;
    String tag_name;
    boolean suggested;
    
    public Correction(int pos,String tag_name,int total_matches,int length,boolean suggested) {
        this.pos = pos;
        this.tag_name = tag_name;
        this.total_matches= total_matches;
	     this.length = length;
    }
	

    
    public int getPos() {
        return pos;
    }

    public String getTag_name() {
        return tag_name;
    }
    
    public int getTotal_matches() {
        return total_matches;
    }
	
    public int getLength() {
        return length;
    }
    
    public boolean getSuggested() {
        return suggested;
    }

}