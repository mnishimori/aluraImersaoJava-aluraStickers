import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.font.TextLayout;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;

public class GeradorDeFigurinhas {

    public static void main(String[] args) throws Exception {
        GeradorDeFigurinhas geradorDeFigurinhas = new GeradorDeFigurinhas();
        //geradorDeFigurinhas.criarFigurinha();

        //getImpactFont();

        /*GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
        String[] availableFontFamilyNames = ge.getAvailableFontFamilyNames();
        for (String fontName : availableFontFamilyNames) {
            System.out.println(fontName);
        }*/

    }

    public void criarFigurinha(InputStream is, String fileName) throws IOException {
        // leitura da imagem original
        BufferedImage originalImage = ImageIO.read(is);
        // cria a nova imagem em memória com transparência e novo tamanho
        int newHeight = originalImage.getHeight() + 200;
        BufferedImage newImage = new BufferedImage(originalImage.getWidth(), newHeight, BufferedImage.TRANSLUCENT);

        if (newImage != null){
            // copia a imagem original para a nova imagem em memória
            Graphics2D graphics = (Graphics2D) newImage.getGraphics();
            boolean drawImage = graphics.drawImage(originalImage, 0, 0, null);

            if (drawImage){
                // escreve uma frase na nova imagem
                getImpactFont();
                Font font = new Font("Impact", Font.BOLD, 64);
                graphics.setColor(Color.yellow);
                graphics.setFont(font);

                String title = "Imersão Java";
                TextLayout textLayout = new TextLayout(title, graphics.getFont(), graphics.getFontRenderContext());
                double textHeight = newHeight-100;
                double textWidth = textLayout.getBounds().getWidth();

                graphics.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING,
                        RenderingHints.VALUE_TEXT_ANTIALIAS_ON);

                // Draw the text in the center of the image
                graphics.drawString(title, originalImage.getWidth() / 2 - (int) textWidth / 2,
                        newHeight / 2 + (int) textHeight / 2);

                // salva a nova imagem em um arquivo
                File directory = new File("./saida");
                if (!directory.exists()) {
                    directory.mkdir();
                }
                ImageIO.write(newImage, "png", new File("./saida/" + fileName + ".png"));
            }
        }
    }

    private static void getImpactFont() {
        GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
        try {
            ge.registerFont(Font.createFont(Font.TRUETYPE_FONT, new File("./fonts/impact.ttf")));
        } catch (FontFormatException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
