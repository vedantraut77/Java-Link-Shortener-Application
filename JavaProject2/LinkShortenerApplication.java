import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

class LinkStorage {
    private java.util.Map<String, LinkModel> linkMap;

    public LinkStorage() {
        this.linkMap = new java.util.HashMap<>();
    }

    public void saveLink(LinkModel link) {
        linkMap.put(link.getShortCode(), link);
    }

    public LinkModel getLink(String shortCode) {
        return linkMap.get(shortCode);
    }
}

class LinkShortenerService {
    private LinkStorage linkStorage;
    private int counter;

    public LinkShortenerService() {
        this.linkStorage = new LinkStorage();
        this.counter = 1;
    }

    public String shortenLink(String originalUrl) {
        if (isValidUrl(originalUrl)) {
            String shortCode = generateShortCode();
            LinkModel link = new LinkModel(originalUrl, shortCode);
            linkStorage.saveLink(link);
            return shortCode;
        } else {
            System.out.println("Invalid URL. Please enter a valid URL.");
            return null;
        }
    }

    public String expandLink(String shortCode) {
        LinkModel link = linkStorage.getLink(shortCode);
        return (link != null) ? link.getOriginalUrl() : null;
    }

    private String generateShortCode() {
        return "short" + counter++;
    }

    private boolean isValidUrl(String url) {
        try {
            new URL(url);
            return true;
        } catch (MalformedURLException e) {
            return false;
        }
    }
}

public class LinkShortenerApplication {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        LinkShortenerService linkShortenerService = new LinkShortenerService();

        // Take input for the original link
        System.out.print("Enter the original URL: ");
        String originalUrl = scanner.nextLine();

        // Shorten a link if the URL is valid
        String shortCode = linkShortenerService.shortenLink(originalUrl);

        if (shortCode != null) {
            System.out.println("Shortened Link: " + shortCode);

            // Expand a link
            System.out.print("Enter the short code to expand the link: ");
            String inputShortCode = scanner.nextLine();
            String expandedLink = linkShortenerService.expandLink(inputShortCode);

            if (expandedLink != null) {
                System.out.println("Expanded Link: " + expandedLink);
            } else {
                System.out.println("Invalid short code. Unable to expand the link.");
            }
        }

        scanner.close();
    }
}
