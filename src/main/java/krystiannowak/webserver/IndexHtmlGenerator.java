package krystiannowak.webserver;

import java.io.File;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

import com.google.common.base.Splitter;

/**
 * A generator for an HTML index page showing the content of given directory.
 *
 * @author krystiannowak
 *
 */
public class IndexHtmlGenerator {

    /**
     * Standard HTML doctype for the listing output.
     */
    private static final String DOCTYPE = "<!DOCTYPE HTML PUBLIC "
            + "\"-//W3C//DTD HTML 3.2 Final//EN\">";

    /**
     * Newline shortcut.
     */
    private static final char NEW_LINE = '\n';

    /**
     * The document root.
     */
    private final File documentRoot;

    /**
     * The subdirectory inside the document root.
     */
    private final String subdirectory;

    /**
     * An {@link Optional} of Host HTTP header.
     */
    private final Optional<String> host;

    /**
     * Creates this index generator.
     *
     * @param documentRoot
     *            the document root to look for files and folders
     * @param subdirectory
     *            relative to documentRoot
     * @param host
     *            an {@link Optional} of Host header value
     */
    public IndexHtmlGenerator(final File documentRoot,
            final String subdirectory, final Optional<String> host) {
        this.documentRoot = documentRoot;
        this.subdirectory = subdirectory;
        this.host = host;
    }

    /**
     * Gets the rendered HTML representation of index of the directory.
     *
     * @return the HTML index
     */
    public final String getHtml() {
        StringBuilder sb = new StringBuilder();
        renderDocument(sb);
        return sb.toString();
    }

    /**
     * Renders the HTML document.
     *
     * @param sb
     *            a {@link StringBuilder} for the HTML document
     */
    private void renderDocument(final StringBuilder sb) {
        sb.append(DOCTYPE);
        sb.append(NEW_LINE);
        sb.append("<html>");
        sb.append(NEW_LINE);
        renderHead(sb);
        renderBody(sb);
        sb.append("</html>");
        sb.append(NEW_LINE);
    }

    /**
     * Renders the HTML head tag and its content.
     *
     * @param sb
     *            a {@link StringBuilder} for the HTML document
     */
    private void renderHead(final StringBuilder sb) {
        sb.append("<head>");
        sb.append(NEW_LINE);
        sb.append("<title>");
        sb.append("Index of ");
        sb.append(subdirectory);
        sb.append("</title>");
        sb.append(NEW_LINE);
        sb.append("</head>");
        sb.append(NEW_LINE);
    }

    /**
     * Renders the HTML body tag and its content.
     *
     * @param sb
     *            a {@link StringBuilder} for the HTML document
     */
    private void renderBody(final StringBuilder sb) {
        sb.append("<body>");
        sb.append(NEW_LINE);
        renderH1(sb);
        renderTable(sb);
        renderAddress(sb);
        sb.append("</body>");
        sb.append(NEW_LINE);
    }

    /**
     * Renders the HTML h1 tag and its content.
     *
     * @param sb
     *            a {@link StringBuilder} for the HTML document
     */
    private void renderH1(final StringBuilder sb) {
        sb.append("<h1>");
        sb.append("Index of ");
        sb.append(subdirectory);
        sb.append("</h1>");
        sb.append(NEW_LINE);
    }

    /**
     * Renders the HTML table tag and its content.
     *
     * @param sb
     *            a {@link StringBuilder} for the HTML document
     */
    private void renderTable(final StringBuilder sb) {
        sb.append("<table>");
        sb.append(NEW_LINE);
        renderTableHeader(sb);
        renderTableLine(sb);
        renderTableData(sb);
        renderTableLine(sb);
        sb.append("</table>");
        sb.append(NEW_LINE);
    }

    /**
     * Renders the HTML table header row tag and its content.
     *
     * @param sb
     *            a {@link StringBuilder} for the HTML document
     */
    private void renderTableHeader(final StringBuilder sb) {
        sb.append("<tr>");
        sb.append("<th>");
        sb.append("Name");
        sb.append("</th>");
        sb.append("</tr>");
        sb.append(NEW_LINE);
    }

    /**
     * Renders the HTML table row tag with a separator.
     *
     * @param sb
     *            a {@link StringBuilder} for the HTML document
     */
    private void renderTableLine(final StringBuilder sb) {
        sb.append("<tr>");
        sb.append("<th>");
        sb.append("<hr>");
        sb.append("</th>");
        sb.append("</tr>");
        sb.append(NEW_LINE);
    }

    /**
     * Renders the HTML table data rows.
     *
     * @param sb
     *            a {@link StringBuilder} for the HTML document
     */
    private void renderTableData(final StringBuilder sb) {
        File dir = new File(documentRoot, subdirectory);

        if (!subdirectory.equals("/")) {
            renderTableBackLink(sb, dir);
        }

        File[] files = dir.listFiles();
        if (files != null) {
            List<File> fileList = Arrays.asList(files);
            fileList.sort(Comparator.comparing(File::isFile)
                    .thenComparing(File::getName));

            for (File file : fileList) {
                renderTableDataLine(sb, file);
            }
        }
    }

    /**
     * Renders the HTML table row with back link.
     *
     * @param sb
     *            a {@link StringBuilder} for the HTML document
     * @param dir
     *            a directory for which to look for back link to its parent
     */
    private void renderTableBackLink(final StringBuilder sb, final File dir) {
        String backLink = "/"
                + Paths.get(documentRoot.getAbsolutePath())
                        .relativize(Paths
                                .get(dir.getParentFile().getAbsolutePath()))
                .toString();

        if (!backLink.equals("/")) {
            backLink += "/";
        }

        renderTableDataLine(sb, backLink, "Parent Directory");
    }

    /**
     * Renders the HTML table row with file data.
     *
     * @param sb
     *            a {@link StringBuilder} for the HTML document
     * @param file
     *            a file or directory for which to generate this row with name
     *            and link
     */
    private void renderTableDataLine(final StringBuilder sb, final File file) {
        String fileName = file.getName();
        if (file.isDirectory()) {
            fileName += "/";
        }

        renderTableDataLine(sb, fileName, fileName);
    }

    /**
     * Renders the HTML table row with link and label.
     *
     * @param sb
     *            a {@link StringBuilder} for the HTML document
     * @param link
     *            link to be used in this row
     * @param label
     *            label to be used in this row
     */
    private void renderTableDataLine(final StringBuilder sb, final String link,
            final String label) {
        sb.append("<tr>");
        sb.append("<td>");
        sb.append("<a href=\"");
        sb.append(link);
        sb.append("\">");
        sb.append(label);
        sb.append("</a>");
        sb.append("</td>");
        sb.append("</tr>");
        sb.append(NEW_LINE);
    }

    /**
     * Renders the HTML address tag and its content.
     *
     * @param sb
     *            a {@link StringBuilder} for the HTML document
     */
    private void renderAddress(final StringBuilder sb) {
        host.ifPresent(h -> {

            sb.append("<address>");

            List<String> items = Splitter.on(':').splitToList(h);
            if (items.size() > 0) {
                sb.append("Web Server at ");
                sb.append(items.get(0));

                if (items.size() > 1) {
                    sb.append(" Port ");
                    sb.append(items.get(1));
                }
            }

            sb.append("</address>");
            sb.append(NEW_LINE);
        });
    }
}
