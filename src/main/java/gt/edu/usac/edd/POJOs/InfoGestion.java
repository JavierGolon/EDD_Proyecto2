package gt.edu.usac.edd.POJOs;

public class InfoGestion extends Generico {
	private Integer mLlave = null;
    private String fecha=null;
    private String hora=null;
    private Double Costo=null;
    private String NombreUs=null;
    private String CodigoUs=null;
    private String Descrip=null;
    

     public InfoGestion(int pValor) {
        mLlave = new Integer(pValor);
    }
    
    public InfoGestion(Integer pValor,String fe,String time,Double Cos,String name,String cod,String des){
        mLlave= pValor;
        fecha=fe;
        hora=time;
        Costo=Cos;
        NombreUs=name;
        CodigoUs=cod;
        Descrip=des;
    }

  

    public Object getKey() {
        return mLlave;
    }
    
    public Object getName(){
        return NombreUs;
    }
    
    public Object getFecha(){
        return fecha;
    }
    
    public Object getHora(){
        return hora;
    }
    
    public Object getCosto(){
        return Costo;
    }
    
    public Object getCodigo(){
        return CodigoUs;
    }
    public Object getDesc(){
        return Descrip;
    }
    
    public void SetName(String n){
        NombreUs=n;
    }
            
    
    
    public boolean igualA(Generico pObjeto) {
        return mLlave.equals(pObjeto.getKey());
    }

    public boolean menorQue(Generico pObjeto) {
        return mLlave.compareTo((Integer)pObjeto.getKey()) < 0;
    }
    
    public boolean mayorQue(Generico pObjeto) {
        return mLlave.compareTo((Integer)pObjeto.getKey()) > 0;
    }
    
    public boolean menorOIgualQue(Generico pObjeto) {
        return mLlave.compareTo((Integer)pObjeto.getKey()) <= 0;
    }
  
    public boolean mayorOIgualQue(Generico pObjeto) {
        return mLlave.compareTo((Integer)pObjeto.getKey()) >= 0;
    }
    
    public Generico minKey() {
        return new InfoGestion(Integer.MIN_VALUE);
    }

}
