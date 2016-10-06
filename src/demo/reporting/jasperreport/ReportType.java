package demo.reporting.jasperreport;

public class ReportType {
	private String value;
	private String label;

	public ReportType(String label, String value) {
		super();
		this.value = value;
		this.label = label;
	}

	public String getValue() {
		return value;
	}

	public String getLabel() {
		return label;
	}
}