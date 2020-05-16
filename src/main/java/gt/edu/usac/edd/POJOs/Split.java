package gt.edu.usac.edd.POJOs;

public class Split {
	NodoB mPuntero;
    Generico mLlave;
    Object mDato;

    public Split(NodoB pPuntero, Generico pLlave, Object pDato) {
        this.mPuntero = pPuntero;
        this.mLlave = pLlave;
        this.mDato = pDato;
    }

    public void setPuntero(NodoB mPuntero) {
        this.mPuntero = mPuntero;
    }

    public NodoB getPuntero() {
        return mPuntero;
    }

    public void setLlave(Generico mLlave) {
        this.mLlave = mLlave;
    }

    public Generico getLlave() {
        return mLlave;
    }

    public void setDato(Object mDato) {
        this.mDato = mDato;
    }

    public Object getDato() {
        return mDato;
    }
}
