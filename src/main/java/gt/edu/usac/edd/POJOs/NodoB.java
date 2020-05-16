package gt.edu.usac.edd.POJOs;

public class NodoB {
	 int mK;
	    int mB;
	    Generico[] mLlaves;
	    Object[] mDatos;
	    NodoB[] mPunteros;

	    private static int numeroDeNodo = 1;
	    
	    public String getDotName() {
	        return "Nodo" + this.hashCode();
	    }
	    
	    public String toDot(  )  {
	        
	        StringBuilder b = new StringBuilder();
	        
	        b.append( getDotName() );
	        b.append("[label=\"<P0>");
	        for( int i = 0; i < mB; i++ ) {
	            b.append( "|" +  mLlaves[i].getName()+"\n"+mLlaves[i].getKey().toString());
	            b.append( "|<P" + (i+1) + ">" );                
	        }
	        
	        b.append("\"];\n");
	        
	        for( int i = 0; i <= mB ; i++ ) {
	            if( mPunteros[i] != null )   {
	                b.append( mPunteros[i].toDot() );
	                b.append( getDotName() + ":P" + i + " -> " + mPunteros[i].getDotName() + ";\n" );
	            }
	        }
	        
	        return b.toString();
	        
	    }
	    
	    public NodoB(int pK) {
	        this.mK = pK;
	        mB = 0;
	        mLlaves = new Generico[2 * pK + 1];
	        mDatos = new Object[2 * pK + 1];
	        mPunteros = new NodoB[(2 * pK) + 2];
	    }

	    public NodoB(int pK, Generico pLlave, Object pDato) {
	        this(pK);
	        mB = 1;
	        mLlaves[0] = pLlave;
	        mDatos[0] = pDato;
	    }

	    public void setK(int mK) {
	        this.mK = mK;
	    }

	    public int getK() {
	        return mK;
	    }
}
