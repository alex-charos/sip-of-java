
public class StringEnhancementsIV {

	public static void main(String[] args) {

		String aStringToTransform = "Transform me!";
		String transformedString = aStringToTransform.transform(p->p.toUpperCase());

		System.out.println(transformedString);
	}

}
