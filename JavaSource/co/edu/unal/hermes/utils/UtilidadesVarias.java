
package co.edu.unal.hermes.utils;


public class UtilidadesVarias {

    public static boolean  seTranslapanIntervalos(int primeraCoordenada1,int segundaCoordenada1,int primeraCoordenada2,
            								int segundaCoordenada2 )
    {
        String [] m=menor(primeraCoordenada1,primeraCoordenada2,segundaCoordenada1,segundaCoordenada2);
        if(m[0].startsWith("c11"))
        {
            if(segundaCoordenada1>menor(primeraCoordenada2,segundaCoordenada2))
            {
                return true;
            }
        }
        if(m[0].startsWith("c12"))
        {
            if(primeraCoordenada1>menor(primeraCoordenada2,segundaCoordenada2))
            {
                return true;
            }
        }
        if(m[0].startsWith("c21"))
        {
            if(segundaCoordenada2>menor(primeraCoordenada1,segundaCoordenada1))
            {
                return true;
            }
        }
        if(m[0].startsWith("c22"))
        {
            if(primeraCoordenada2>menor(primeraCoordenada1,segundaCoordenada1))
            {
                return true;
            }
        }
        return false; 
//        if(primeraCoordenada1 == primeraCoordenada2 && segundaCoordenada1==segundaCoordenada2)
//        {
//            return true;
//        }
//        if((primeraCoordenada1 < segundaCoordenada1 && primeraCoordenada1> segundaCoordenada2)
//                || (primeraCoordenada1 > segundaCoordenada1 && primeraCoordenada1< segundaCoordenada2)
//                || (primeraCoordenada2 < segundaCoordenada1 && primeraCoordenada2> segundaCoordenada2)
//                || (primeraCoordenada2 > segundaCoordenada1 && primeraCoordenada2< segundaCoordenada2)
//           //
//		|| (segundaCoordenada1 < primeraCoordenada1 && segundaCoordenada1> primeraCoordenada2)
//		|| (segundaCoordenada1 > primeraCoordenada1 && segundaCoordenada1< primeraCoordenada2)
//		|| (segundaCoordenada2 < primeraCoordenada1 && segundaCoordenada2> primeraCoordenada2)
//		|| (segundaCoordenada2 > primeraCoordenada1 && segundaCoordenada2< primeraCoordenada2)
//        )
//        {
//            return true;
//        }
//        return false;
    }

    /**
     * siempre que la segunda coordenada sea mayor que la primera
     * @param primeraCoordenada1
     * @param segundaCoordenada1
     * @param primeraCoordenada2
     * @param segundaCoordenada2
     * @return
     */
    public static boolean  seTranslapanIntervalosInclusivo(int primeraCoordenada1,int segundaCoordenada1,int primeraCoordenada2,
			int segundaCoordenada2 )
	{
		
        
        
        if(primeraCoordenada1 == primeraCoordenada2 || segundaCoordenada1==primeraCoordenada2
		        || primeraCoordenada1==segundaCoordenada2 || segundaCoordenada1==segundaCoordenada2 
		)
		{
		    return true;
		}
		
        String [] m=menor(primeraCoordenada1,primeraCoordenada2,segundaCoordenada1,segundaCoordenada2);
        if(m[0].startsWith("c11"))
        {
            if(segundaCoordenada1>menor(primeraCoordenada2,segundaCoordenada2))
            {
                return true;
            }
        }
        if(m[0].startsWith("c12"))
        {
            if(primeraCoordenada1>menor(primeraCoordenada2,segundaCoordenada2))
            {
                return true;
            }
        }
        if(m[0].startsWith("c21"))
        {
            if(segundaCoordenada2>menor(primeraCoordenada1,segundaCoordenada1))
            {
                return true;
            }
        }
        if(m[0].startsWith("c22"))
        {
            if(primeraCoordenada2>menor(primeraCoordenada1,segundaCoordenada1))
            {
                return true;
            }
        }
//        if((primeraCoordenada1 < segundaCoordenada1 &&  primeraCoordenada2 < segundaCoordenada2)
//        {
//		    if(primeraCoordenada1<primeraCoordenada2 && segundaCoordenada1>primeraCoordenada2)
//		    {
//		        return true;
//		    }
//		    if(primeraCoordenada1<segundaCoordenada2 && segundaCoordenada2<segundaCoordenada1)
//		    {
//		        return true;
//		    }
//		    
//        }
//			|| (primeraCoordenada1 > segundaCoordenada1 && primeraCoordenada1< segundaCoordenada2)
//			|| (primeraCoordenada2 < segundaCoordenada1 && primeraCoordenada2> segundaCoordenada2)
//			|| (primeraCoordenada2 > segundaCoordenada1 && primeraCoordenada2< segundaCoordenada2)
//			//
//			|| (segundaCoordenada1 < primeraCoordenada1 && segundaCoordenada1> primeraCoordenada2)
//			|| (segundaCoordenada1 > primeraCoordenada1 && segundaCoordenada1< primeraCoordenada2)
//			|| (segundaCoordenada2 < primeraCoordenada1 && segundaCoordenada2> primeraCoordenada2)
//			|| (segundaCoordenada2 > primeraCoordenada1 && segundaCoordenada2< primeraCoordenada2)
//			)
//		{
//		    return true;
//		}
		return false;
	}
    
    public static String[] menor(int c11,int c12,int c21,int c22)
    {
        int menor=c11;
        String menorS="c11";
        if(c11>c12)
        {
            menorS="c12";
            menor=c12;
        }
        if(c21<menor)
        {
            menorS="c21";
            menor=c21;
        }
        if(c22<menor)
        {
            menorS="c22";
            menor=c22;
        }
        String [] r={menorS,String.valueOf(menor)};
        return r; 
        
        
    }
    
    public static int menor(int c1,int c2)
    {
        if(c1<c2)
            return c1;
        return c2;
    }
    public int mayor(int c1,int c2)
    {
        if(c1<c2)
            return c2; 
        return c1;
    }
}
