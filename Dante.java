import java.util.*;

public class Dante{
    private ArrayList<String> versiTerzine;
    private Dizionario        dizionario;

    public Dante(Dizionario dizionario){
        this.dizionario = dizionario;
    }

    private boolean isVocal(char c){
        if(c=='a' || c=='e' || c=='i' || c=='o' || c=='u')
            return true;
        else return false;
    }

    private boolean consInsm(String a, int i){
        if( a.charAt(i)=='c' && a.charAt(i+1)=='h' ){
            return true;
        }
        if(a.charAt(i)=='g' && a.charAt(i+1)=='h'){
            return true;
        }
        if(a.charAt(i)=='g' && a.charAt(i+1)=='n'){
            return true;
        }
        if(a.charAt(i)=='g' && a.charAt(i+1)=='h'){
            return true;
        }
        if(a.charAt(i)=='s' && a.charAt(i+1)!='s' && !isVocal(a.charAt(i+1))){
            return true;
        }
        if(a.charAt(i)=='t' && a.charAt(i+1)=='r'){
            return true;
        }
        if(a.charAt(i)=='c' && a.charAt(i+1)=='r'){
            return true;
        }
        if(a.charAt(i)=='p' && a.charAt(i+1)=='r'){
            return true;
        }
        if(a.charAt(i)=='b' && a.charAt(i+1)=='r'){
            return true;
        }
        else return false;
    }

    private boolean vocInsm(String a, int i){
        return true;
    }

    private boolean isArticolo(String a){
        if(a.equals("lo") || a.equals("la") || a.equals("i") ||
            a.equals("gli") || a.equals("le") || a.equals("uno") ||
            a.equals("una") || a.equals("dei"))
            return true;
        else return false;
    }

    private boolean beneFine(String a){
        if(!isVocal(a.charAt(a.length()-1)) || isArticolo(a))
            return false;
        else return true;
    }

    private String[] divSil(String verso){
        StringBuffer      str = new StringBuffer("");
        String appo;
        ArrayList<String> sill = new ArrayList<>();
        Scanner           word = new Scanner(verso);

        //sill.add("ciao");
        //Nel mezzo del cammin di nostra vita
        //Nel-mez-zodelcam-mindinostravita
        // => Nel-mez-zo-del-cam-min-di-no-tra-vi-ta

        /* allor fu la paura un poco queta,
           allorfulapauraunpocoqueta
        */

        //tolgo spazi
        while(word.hasNext())
            str.append(word.next());
        
        appo = str.toString();
        
        //divido in piu piccole particelle secondo le doppie
        for(int i=0, k=0; i<appo.length()-1; i++){
            if((!isVocal(appo.charAt(i)) && appo.charAt(i)==appo.charAt(i+1)) ||
               (appo.charAt(i)=='c' && appo.charAt(i+1)=='q')){
                sill.add(appo.substring(k,i));
                k = i+1;
            }
        }
        int count=0;
        for(int j=0; j<sill.size(); j++){
            int k;
            k=0;
            for(int i=0; i<sill.get(j).length()-1; i++){
                if(!consInsm(sill.get(j), i) || !vocInsm(sill.get(j), i)){
                    sill.add(count+1, sill.get(j).substring(i+1, sill.get(j).length()-1));
                    sill.set(count, sill.get(count).substring(k, i));
                    k=i+1;
                }

            }
            count++;
        }

        /*for(int i=0; (i-3)<verso.length(); ){
            if(!isVocal(appo.charAt(i)) && isVocal(appo.charAt(i+1))
            && !isVocal(appo.charAt(i+2)) && appo.charAt(i+2)!='s'
            && isVocal(appo.charAt(i+3))){
                str = new StringBuffer(appo.charAt(i)+appo.charAt(i+1));
                i+=2;
            }
            else if(!isVocal(appo.charAt(i)) && isVocal(appo.charAt(i+1))
            && !isVocal(appo.charAt(i+2)))
        }*/
            



        return sill.toArray(new String[sill.size()]);
    }
    private int contaSillabe(String verso){
        String[] sill = this.divSil(verso);
        return sill.length;
    }

    private boolean isVersoRima(String v1, String v2){
        int v1Leng = v1.length();
        int v2Leng = v2.length();

        if(!isVocal(v1.charAt(v1Leng-1)) && !isVocal(v1.charAt(v1Leng-2)))
            if(v1.substring(v1Leng-3, v1Leng).equals(v2.substring(v2Leng-3, v2Leng)))
                return true;
            else return false;
        else if(!isVocal(v1.charAt(v1Leng-2)) && !isVocal(v1.charAt(v1Leng-3)))
            if(v1.substring(v1.length()-4, v1.length()).equals(v2.substring(v2.length()-4, v2.length())))
                return true;
            else return false;
        else{
            if(v1.substring(v1.length()-3, v1.length()).equals(v2.substring(v2.length()-3, v2.length())))
                return true;
            else return false;
        }
    }

    private String creaVerso(int sillabe){
        StringBuffer str = new StringBuffer("");
        
        while(this.contaSillabe(str.toString())!=sillabe){
            str = new StringBuffer("");
            while(this.contaSillabe(str.toString())<sillabe){

                str.append(this.dizionario.estrapolaParola()+" ");                
                
                if(this.contaSillabe(str.toString())>=sillabe){
                    //controllo che l'ultima parola vada bene
                    //System.out.println(str.toString());
                    for(int j=str.length()-2; j>=0; j--){
                        //System.out.println(j);
                        if(str.charAt(j)==' '){
                            //System.out.println(str.toString().substring(j+1, str.length()-1));
                            if(!beneFine(str.toString().substring(j+1, str.length()-1))){
                                str = new StringBuffer("");
                            }
                            break;
                        }
                    }
                }
            }            
        }
        
        return str.toString().substring(0, str.length()-1);
    }

    public String produciDivinaMerda(int sillabe, int terzine){
        StringBuffer str = new StringBuffer("");
        boolean isAnte = false;
        String appoVerso, ante;

        this.versiTerzine.add(creaVerso(sillabe));
        this.versiTerzine.add(creaVerso(sillabe));
        appoVerso = creaVerso(sillabe);
        ante = this.versiTerzine.get(0);
        //System.out.println(this.versiTerzine.get(0)+'\n');
        while(!this.isVersoRima(ante, appoVerso)){
            appoVerso = creaVerso(sillabe);
            //System.out.println(appoVerso);
        }
        this.versiTerzine.add(appoVerso);
        
        //System.out.println(this.versiTerzine.get(1));
        //System.out.println(this.versiTerzine.get(2));

        //for(int i=1; i<terzine; i++){
            for(int j=3; j<terzine*3; j++){
				if(((j+1)%2==0 && ((j+1)/2)%2==1) || ((j+1)%2==1 && ((j+1)/2)%2==0))
		            isAnte = true;
		        if(((j+1)%2==0 && ((j+1)/2)%2==0) || ((j+1)%2==1 && ((j+1)/2)%2==1))
		            isAnte = false;
				if(isAnte==true)
	                this.versiTerzine.add(creaVerso(sillabe));
	            else{
	                appoVerso = creaVerso(sillabe);
	                ante = this.versiTerzine.get(j-2);
	                while(!this.isVersoRima(ante, appoVerso))
	                    appoVerso = creaVerso(sillabe);
	                this.versiTerzine.add(appoVerso);
	            }
            }
        //}

        for(int i=0; i<this.versiTerzine.size(); i++){
            str.append(this.versiTerzine.get(i));
            if(i%3==2) str.append(".\n\n");
            else str.append("\n");
        }

        return str.toString();
    }

    
}