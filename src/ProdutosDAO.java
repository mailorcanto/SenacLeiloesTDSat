

import java.sql.PreparedStatement;
import java.sql.Connection;
import java.sql.SQLException;
import javax.swing.JOptionPane;
import java.sql.ResultSet;
import java.util.ArrayList;

public class ProdutosDAO {
    
    Connection conn;
    PreparedStatement prep;
    ArrayList<ProdutosDTO> listagem = new ArrayList<>();
    
    public int cadastrarProduto(ProdutosDTO produto) {
        try {
            conn = new conectaDAO().connectDB();
            prep = conn.prepareStatement("INSERT INTO produtos (nome, valor, status) VALUES(?, ?, ?)");

            prep.setString(1, produto.getNome());
            prep.setInt(2, produto.getValor());
            prep.setString(3, produto.getStatus());

            int status = prep.executeUpdate();

            if (status > 0) {
                JOptionPane.showMessageDialog(null, "Produto cadastrado com sucesso!");
            } else {
                JOptionPane.showMessageDialog(null, "Falha ao cadastrar o produto.");
            }

            return status; 
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Erro ao cadastrar produto: " + ex.getMessage());
            return 0; 
        } finally {
            try {
                if (prep != null) prep.close();
                if (conn != null) conn.close();
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(null, "Erro ao fechar a conexão: " + e.getMessage());
            }
        }
    }

    public ArrayList<ProdutosDTO> listarProdutos() {
        return listagem;
    }
}


