

import java.sql.PreparedStatement;
import java.sql.Connection;
import java.sql.SQLException;
import javax.swing.JOptionPane;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class ProdutosDAO {
    
    Connection conn;
    PreparedStatement prep;
    ResultSet rs;
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

        String sql = "SELECT id, nome, valor, status FROM produtos";
        ArrayList<ProdutosDTO> listagem = new ArrayList<>();
        
        try {
            conn = new conectaDAO().connectDB();
            PreparedStatement stmt = this.conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();
            
            while(rs.next()){
                ProdutosDTO produto = new ProdutosDTO();

                produto.setId(rs.getInt("id"));
                produto.setNome(rs.getString("nome"));
                produto.setValor(rs.getInt("valor"));
                produto.setStatus(rs.getString("status"));

                listagem.add(produto);

            }
        } catch (Exception e) {
            return null;
        }
        return listagem;
    }
    
    public boolean venderProduto(int id) {
        String sql = "UPDATE produtos SET status = ? WHERE id = ?";
        try {
            conn = new conectaDAO().connectDB();
            prep = conn.prepareStatement(sql);
            prep.setString(1, "Vendido"); // Define o novo status
            prep.setInt(2, id); // Define o ID do produto

            int rowsUpdated = prep.executeUpdate();
            if (rowsUpdated > 0) {
                JOptionPane.showMessageDialog(null, "Produto vendido com sucesso!");
                return true;
            } else {
                JOptionPane.showMessageDialog(null, "Nenhum produto encontrado com o ID informado.");
                return false;
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Erro ao vender produto: " + e.getMessage());
            return false;
        } finally {
            try {
                if (prep != null) prep.close();
                if (conn != null) conn.close();
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(null, "Erro ao fechar conexão: " + e.getMessage());
            }
        }
    }

    
    public ArrayList<ProdutosDTO> listarProdutosVendidos() {
        String sql = "SELECT id, nome, valor, status FROM produtos WHERE status = 'Vendido'";
        ArrayList<ProdutosDTO> listagem = new ArrayList<>();

        try {
            conn = new conectaDAO().connectDB();
            PreparedStatement stmt = this.conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();

            while(rs.next()){
                ProdutosDTO produto = new ProdutosDTO();

                produto.setId(rs.getInt("id"));
                produto.setNome(rs.getString("nome"));
                produto.setValor(rs.getInt("valor"));
                produto.setStatus(rs.getString("status"));

                listagem.add(produto);
            }
        } catch (Exception e) {
            return null;
        }
        return listagem;
        }

}


