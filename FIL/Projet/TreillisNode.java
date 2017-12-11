
public class TreillisNode {
	
	private int codemot;
	private double prob_emission;
	private double alpha;
	private TreillisNode beta;
	
	//Methods
	public TreillisNode(int code, double prob)
	{
		codemot = code;
		prob_emission = prob;
	}
	
	public int getCodemot()
	{
		return codemot;
	}
	
	public double getProbEmission()
	{
		return prob_emission;
	}
	
	public double getAlpha()
	{
		return alpha;
	}
	
	public void setAlpha(double a)
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
