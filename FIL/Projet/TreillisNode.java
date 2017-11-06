
public class TreillisNode {
	
	private int codemot;
	private float prob_emission;
	private float alpha;
	private TreillisNode beta;
	
	//Methods
	public TreillisNode(int code, float prob)
	{
		codemot = code;
		prob_emission = prob;
	}
	
	public int getCodemot()
	{
		return codemot;
	}
	
	public float getProbEmission()
	{
		return prob_emission;
	}
	
	public float getAlpha()
	{
		return alpha;
	}
	
	public void setAlpha(float a)
	{
		alpha = a;
	}
	
	public TreillisNode getBeta()
	{
		return beta;
	}
	
	public void setBeta(TreillisNode n)
	{
		beta = n;
	}
}
