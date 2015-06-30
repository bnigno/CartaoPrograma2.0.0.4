package pa.pm.cartaoprograma2;

public class ItemListView
{
    private String texto;
    private String texto2;
    private double lat;
    private double lng;
    private int iconeRid;
 
    public ItemListView()
    {
    }
 
    public ItemListView(String texto, int iconeRid)
    {
        this.texto = texto;
        this.iconeRid = iconeRid;
    }
 
    public int getIconeRid()
    {
        return iconeRid;
    }
 
    public void setIconeRid(int iconeRid)
    {
        this.iconeRid = iconeRid;
    }
 
    public String getTexto()
    {
        return texto;
    }
 
    public void setTexto(String texto)
    {
        this.texto = texto;
    }
    public void setTexto2(String texto)
    {
        this.texto2 = texto;
    }
    public void setLoc(Double lat,Double lng)
    {
        this.lat = lat;
        this.lng = lng;
    }
    public String getTexto2()
    {
        return texto2;
    }
    public double getLat()
    {
    	
    	return lat;
       
    }
    public double getLng()
    {
    	
    	return lng;
       
    }
}