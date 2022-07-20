import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.InputStream;

public class GeradorDeFigurinhas {

    public static void main(String[] args) throws Exception {
        GeradorDeFigurinhas geradorDeFigurinhas = new GeradorDeFigurinhas();
        //geradorDeFigurinhas.criarFigurinha();
    }

    public void criarFigurinha(InputStream is, String nomeArquivo) throws Exception {
        // leitura da imagem original
        // InputStream is = new FileInputStream(new File("./entrada/filme.jpg"));
        // InputStream is = new URL("https://imersao-java-apis.s3.amazonaws.com/TopMovies_2.jpg").openStream();
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
                Font font = new Font(Font.SANS_SERIF, Font.BOLD, 64);
                graphics.setColor(Color.yellow);
                graphics.setFont(font);
                graphics.drawString("Imersão Java", 190, newHeight-100);

                // salva a nova imagem em um arquivo
                ImageIO.write(newImage, "png", new File("./saida/" + nomeArquivo + ".png"));
            }
        }
    }

}
