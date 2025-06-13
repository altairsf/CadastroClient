package cadastroclient;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.List;
import model.Produto;

public class CadastroClient {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws IOException, ClassNotFoundException {
        Socket socket = new Socket("localhost", 4321);
        ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
        ObjectInputStream in = new ObjectInputStream(socket.getInputStream());

        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

        System.out.println("------------------------------");
        System.out.print("Login: ");
        String login = reader.readLine();
        System.out.print("Senha: ");
        String senha = reader.readLine();

        out.writeObject(login);
        out.writeObject(senha);
        out.writeObject("Usuário conectado.");
        out.flush();

        System.out.print("Digite o comando (L): ");
        String comando = reader.readLine();
        out.writeObject(comando);
        out.flush();

        //Lista de produtos
        Object resposta = in.readObject();
        if (resposta instanceof List) {
            List<?> lista = (List<?>) resposta;

            for (Object obj : lista) {
                if (obj instanceof Produto) {
                    Produto p = (Produto) obj;
                    System.out.println("Produto: " + p.getNome());
                }
            }
        } else {
            System.out.println("Erro no servidor: " + resposta);
        }
    }
}
