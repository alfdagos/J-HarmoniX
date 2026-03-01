package it.alf.jharmonix.gui;

import it.alf.jharmonix.core.engine.HarmonyGeneratorService;
import it.alf.jharmonix.core.model.Progression;
import it.alf.jharmonix.core.model.ScaleType;
import it.alf.jharmonix.core.port.ProgressionRequest;
import it.alf.jharmonix.core.port.ProgressionRequest.ComplexityLevel;
import it.alf.jharmonix.core.port.ProgressionRequest.HarmonyStyle;
import it.alf.jharmonix.core.port.ProgressionRequest.ModulationFrequency;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Slider;
import javafx.scene.control.SplitPane;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class JHarmonixGuiApplication extends Application {

  private static final Map<String, ScaleType> SCALE_TYPE_BY_LABEL = new LinkedHashMap<>();
  private static final Map<String, HarmonyStyle> STYLE_BY_LABEL = new LinkedHashMap<>();
  private static final Map<String, ModulationFrequency> MODULATION_BY_LABEL = new LinkedHashMap<>();

  static {
    for (ScaleType type : ScaleType.values()) {
      SCALE_TYPE_BY_LABEL.put(type.getDisplayName(), type);
    }
    STYLE_BY_LABEL.put("Simple", HarmonyStyle.SIMPLE);
    STYLE_BY_LABEL.put("Pop", HarmonyStyle.POP);
    STYLE_BY_LABEL.put("Jazz Standard", HarmonyStyle.JAZZ_STANDARD);
    STYLE_BY_LABEL.put("Jazz Modern", HarmonyStyle.JAZZ_MODERN);

    MODULATION_BY_LABEL.put("None", ModulationFrequency.NONE);
    MODULATION_BY_LABEL.put("Low", ModulationFrequency.LOW);
    MODULATION_BY_LABEL.put("Medium", ModulationFrequency.MEDIUM);
    MODULATION_BY_LABEL.put("High", ModulationFrequency.HIGH);
  }

  private final HarmonyGeneratorService generator = HarmonyGeneratorService.random();

  private ComboBox<String> keyCombo;
  private ComboBox<String> scaleCombo;
  private TextField songFormField;
  private ComboBox<String> styleCombo;
  private ComboBox<String> modulationCombo;
  private ComboBox<String> beatsCombo;
  private Slider complexitySlider;
  private Slider densitySlider;
  private ComboBox<String> grooveCombo;
  private Slider syncopationSlider;

  private TextArea progressionArea;
  private TextArea timelineArea;
  private TextArea voicingArea;
  private Label bpmRangeLabel;
  private Label bpmHintLabel;

  @Override
  public void start(Stage stage) {
    BorderPane root = new BorderPane();
    root.getStyleClass().add("app-root");

    MenuBar menuBar = buildMenuBar();
    HBox topBar = buildTopBar();
    VBox top = new VBox(menuBar, topBar);
    root.setTop(top);

    SplitPane mainSplit = new SplitPane();
    mainSplit.setOrientation(Orientation.HORIZONTAL);
    mainSplit.getItems().addAll(buildInputPanel(), buildOutputPanel(), buildInsightPanel());
    mainSplit.setDividerPositions(0.22, 0.72);
    root.setCenter(mainSplit);

    Scene scene = new Scene(root, 1280, 760);
    scene.getStylesheets().add(loadStylesheet());

    stage.setTitle("J-Harmonix Studio");
    stage.setScene(scene);
    stage.show();
  }

  private MenuBar buildMenuBar() {
    Menu file = new Menu("File");
    file.getItems().addAll(new MenuItem("New Session"), new MenuItem("Open"), new MenuItem("Save"));

    Menu export = new Menu("Export");
    export.getItems().addAll(new MenuItem("MIDI"), new MenuItem("PDF"), new MenuItem("MusicXML"));

    Menu help = new Menu("Help");
    help.getItems().addAll(new MenuItem("Usage Guide"), new MenuItem("About"));

    MenuBar bar = new MenuBar(file, export, help);
    bar.getStyleClass().add("app-menu");
    return bar;
  }

  private HBox buildTopBar() {
    Label title = new Label("J-Harmonix Studio");
    title.getStyleClass().add("app-title");
    title.setFont(Font.font(22));

    ToggleButton livePreview = new ToggleButton("Live Preview");
    livePreview.getStyleClass().add("pill");

    Button generate = new Button("Generate");
    generate.getStyleClass().add("primary");
    generate.setOnAction(event -> generateHarmony());

    Button clear = new Button("Clear");
    clear.getStyleClass().add("ghost");
    clear.setOnAction(event -> clearOutputs());

    Region spacer = new Region();
    HBox.setHgrow(spacer, Priority.ALWAYS);

    HBox topBar = new HBox(16, title, spacer, livePreview, clear, generate);
    topBar.getStyleClass().add("top-bar");
    topBar.setAlignment(Pos.CENTER_LEFT);
    topBar.setPadding(new Insets(14, 20, 14, 20));
    return topBar;
  }

  private VBox buildInputPanel() {
    Label header = new Label("Input");
    header.getStyleClass().add("panel-title");

    keyCombo = combo("C", "Db", "D", "Eb", "E", "F", "Gb", "G", "Ab", "A", "Bb", "B");
    scaleCombo = combo(SCALE_TYPE_BY_LABEL.keySet().toArray(new String[0]));
    scaleCombo.getSelectionModel().select("Major");
    songFormField = textField("AABA");
    styleCombo = combo(STYLE_BY_LABEL.keySet().toArray(new String[0]));
    modulationCombo = combo(MODULATION_BY_LABEL.keySet().toArray(new String[0]));
    beatsCombo = combo("3", "4", "5", "6", "7");
    beatsCombo.getSelectionModel().select("4");

    VBox section1 = panelSection("Generator",
      labeledField("Key", keyCombo),
      labeledField("Scale", scaleCombo),
      labeledField("Form", songFormField),
      labeledField("Style", styleCombo),
      labeledField("Modulation", modulationCombo),
      labeledField("Beats/Bar", beatsCombo)
    );

    complexitySlider = slider(1, 10, 6);
    densitySlider = slider(1, 10, 5);

    VBox section2 = panelSection("Style",
      labeledField("Complexity", complexitySlider),
      labeledField("Density", densitySlider)
    );

    grooveCombo = combo("Swing", "Straight", "Bossa", "Ballad");
    syncopationSlider = slider(1, 10, 4);

    VBox section3 = panelSection("Rhythm",
      labeledField("Groove", grooveCombo),
      labeledField("Syncopation", syncopationSlider)
    );

    VBox panel = new VBox(18, header, section1, section2, section3);
    panel.getStyleClass().add("side-panel");
    panel.setPadding(new Insets(18));
    return panel;
  }

  private VBox buildOutputPanel() {
    Label header = new Label("Harmony Output");
    header.getStyleClass().add("panel-title");

    progressionArea = new TextArea();
    progressionArea.setWrapText(true);

    timelineArea = new TextArea();
    voicingArea = new TextArea();

    TabPane tabs = new TabPane();
    tabs.getTabs().addAll(
      tab("Progression", progressionArea),
      tab("Timeline", timelineArea),
      tab("Voicings", voicingArea)
    );
    tabs.setTabClosingPolicy(TabPane.TabClosingPolicy.UNAVAILABLE);

    VBox panel = new VBox(12, header, tabs);
    panel.getStyleClass().add("main-panel");
    panel.setPadding(new Insets(18));
    VBox.setVgrow(tabs, Priority.ALWAYS);
    return panel;
  }

  private VBox buildInsightPanel() {
    Label header = new Label("Insights");
    header.getStyleClass().add("panel-title");

    bpmRangeLabel = new Label("-- BPM");
    bpmHintLabel = new Label("Generate a progression to see the recommendation");
    VBox bpmCard = infoCard("Recommended BPM",
      bpmRangeLabel,
      bpmHintLabel
    );

    VBox qualityCard = infoCard("Color Profile",
        new Label("Warm / Extended / Modal"),
        new Label("Adds 9ths, 11ths, and chromatic approach")
    );

    VBox tipsCard = infoCard("Suggestions",
        new Label("Try a half-time bridge in Section B"),
        new Label("Increase syncopation for a more modern feel")
    );

    VBox panel = new VBox(18, header, bpmCard, qualityCard, tipsCard);
    panel.getStyleClass().add("side-panel");
    panel.setPadding(new Insets(18));
    return panel;
  }

  private VBox panelSection(String title, HBox... rows) {
    Label header = new Label(title);
    header.getStyleClass().add("section-title");
    VBox box = new VBox(8);
    box.getChildren().add(header);
    box.getChildren().addAll(rows);
    box.getStyleClass().add("section");
    return box;
  }

  private VBox infoCard(String title, Label primary, Label secondary) {
    Label header = new Label(title);
    header.getStyleClass().add("card-title");
    primary.getStyleClass().add("card-primary");
    secondary.getStyleClass().add("card-secondary");

    VBox card = new VBox(6, header, primary, secondary);
    card.getStyleClass().add("card");
    return card;
  }

  private HBox labeledField(String label, Region input) {
    Label field = new Label(label);
    field.getStyleClass().add("field-label");
    HBox row = new HBox(12, field, input);
    row.setAlignment(Pos.CENTER_LEFT);
    HBox.setHgrow(input, Priority.ALWAYS);
    return row;
  }

  private ComboBox<String> combo(String... values) {
    ComboBox<String> combo = new ComboBox<>();
    combo.getItems().addAll(values);
    combo.getSelectionModel().selectFirst();
    combo.getStyleClass().add("input");
    combo.setMaxWidth(Double.MAX_VALUE);
    return combo;
  }

  private TextField textField(String value) {
    TextField field = new TextField(value);
    field.getStyleClass().add("input");
    return field;
  }

  private Slider slider(double min, double max, double value) {
    Slider slider = new Slider(min, max, value);
    slider.getStyleClass().add("input");
    slider.setShowTickLabels(true);
    slider.setShowTickMarks(true);
    return slider;
  }

  private Tab tab(String title, TextArea area) {
    area.getStyleClass().add("output-area");
    Tab tab = new Tab(title, area);
    return tab;
  }

  public static void main(String[] args) {
    launch(args);
  }

  private String loadStylesheet() {
    java.net.URL url = getClass().getResource("/it/alf/jharmonix/gui/styles.css");
    if (url == null) {
      throw new IllegalStateException("Missing stylesheet: /it/alf/jharmonix/gui/styles.css");
    }
    return url.toExternalForm();
  }

  private void generateHarmony() {
    ProgressionRequest request = ProgressionRequest.builder()
        .tonicName(keyCombo.getValue())
        .scaleType(SCALE_TYPE_BY_LABEL.getOrDefault(scaleCombo.getValue(), ScaleType.MAJOR))
        .songForm(normalizeForm(songFormField.getText()))
        .style(STYLE_BY_LABEL.getOrDefault(styleCombo.getValue(), HarmonyStyle.JAZZ_STANDARD))
        .complexity(mapComplexity(complexitySlider.getValue()))
        .modulationFrequency(MODULATION_BY_LABEL.getOrDefault(modulationCombo.getValue(), ModulationFrequency.MEDIUM))
        .beatsPerBar(Integer.parseInt(beatsCombo.getValue()))
        .build();

    List<Progression> progressions = generator.generate(request);
    progressionArea.setText(formatProgressions(progressions));
    timelineArea.setText(formatTimeline(progressions));
    voicingArea.setText(buildVoicingNotes(request.getComplexity()));

    int density = (int) Math.round(densitySlider.getValue());
    int syncopation = (int) Math.round(syncopationSlider.getValue());
    BpmRange bpm = estimateBpm(request, density, grooveCombo.getValue(), syncopation);
    bpmRangeLabel.setText(bpm.min + " - " + bpm.max + " BPM");
    bpmHintLabel.setText(bpm.reason);
  }

  private void clearOutputs() {
    progressionArea.clear();
    timelineArea.clear();
    voicingArea.clear();
    bpmRangeLabel.setText("-- BPM");
    bpmHintLabel.setText("Generate a progression to see the recommendation");
  }

  private String normalizeForm(String form) {
    if (form == null || form.isBlank()) return "AABA";
    return form.trim().toUpperCase(Locale.ROOT);
  }

  private ComplexityLevel mapComplexity(double sliderValue) {
    if (sliderValue <= 2.5) return ComplexityLevel.TRIADS;
    if (sliderValue <= 5.5) return ComplexityLevel.SEVENTH_CHORDS;
    if (sliderValue <= 8.0) return ComplexityLevel.NINTHS;
    return ComplexityLevel.FULL_EXTENSIONS;
  }

  private String formatProgressions(List<Progression> progressions) {
    StringBuilder sb = new StringBuilder();
    for (Progression progression : progressions) {
      sb.append(progression.toString()).append("\n");
    }
    return sb.toString().trim();
  }

  private String formatTimeline(List<Progression> progressions) {
    StringBuilder sb = new StringBuilder();
    for (int i = 0; i < progressions.size(); i++) {
      Progression progression = progressions.get(i);
      String label = progression.getSectionLabel().isBlank() ? "Section " + (i + 1) : progression.getSectionLabel();
      sb.append(label).append(": ").append(progression.size()).append(" chords").append("\n");
    }
    return sb.toString().trim();
  }

  private String buildVoicingNotes(ComplexityLevel complexity) {
    return switch (complexity) {
      case TRIADS -> "Triads and open voicings";
      case SEVENTH_CHORDS -> "Rootless + drop-2 with 7ths";
      case NINTHS -> "Rootless + 9ths and color tones";
      case FULL_EXTENSIONS -> "Extended voicings: 9ths, 11ths, 13ths";
    };
  }

  private BpmRange estimateBpm(ProgressionRequest request, int density, String groove, int syncopation) {
    int base = switch (request.getStyle()) {
      case SIMPLE -> 120;
      case POP -> 110;
      case JAZZ_STANDARD -> 120;
      case JAZZ_MODERN -> 100;
    };

    int grooveBase = switch (groove) {
      case "Swing" -> 120;
      case "Straight" -> 112;
      case "Bossa" -> 96;
      case "Ballad" -> 72;
      default -> 110;
    };

    int complexityPenalty = switch (request.getComplexity()) {
      case TRIADS -> 0;
      case SEVENTH_CHORDS -> 4;
      case NINTHS -> 8;
      case FULL_EXTENSIONS -> 12;
    };

    int densityPenalty = Math.max(0, density - 5) * 2;
    int syncopationBoost = (syncopation - 5) * 2;
    int avg = (base + grooveBase) / 2 - complexityPenalty - densityPenalty + syncopationBoost;

    int min = clamp(avg - 8, 40, 220);
    int max = clamp(avg + 8, 40, 220);
    String reason = "Based on " + groove.toLowerCase(Locale.ROOT)
        + ", complexity, and rhythmic density";
    return new BpmRange(min, max, reason);
  }

  private int clamp(int value, int min, int max) {
    return Math.max(min, Math.min(max, value));
  }

  private static final class BpmRange {
    private final int min;
    private final int max;
    private final String reason;

    private BpmRange(int min, int max, String reason) {
      this.min = min;
      this.max = max;
      this.reason = reason;
    }
  }
}
