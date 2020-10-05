package edu.montana.csci.csci440.model;

import edu.montana.csci.csci440.util.DB;
import edu.montana.csci.csci440.util.Web;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

public class InvoiceItem extends Model {

    long invoiceLineId;
    long invoiceId;
    long trackId;
    BigDecimal unitPrice;
    long quantity;

    private InvoiceItem(ResultSet results) throws SQLException {
        invoiceLineId = results.getLong("InvoiceLineId");
        invoiceId = results.getLong("InvoiceId");
        trackId = results.getLong("TrackId");
        unitPrice = results.getBigDecimal("UnitPrice");
        quantity = results.getLong("Quantity");
    }

    public Track getTrack() {
        return null;
    }
    public Invoice getInvoice() {
        return null;
    }

    public long getInvoiceLineId() {
        return invoiceLineId;
    }

    public void setInvoiceLineId(long invoiceLineId) {
        this.invoiceLineId = invoiceLineId;
    }

    public long getInvoiceId() {
        return invoiceId;
    }

    public void setInvoiceId(long invoiceId) {
        this.invoiceId = invoiceId;
    }

    public long getTrackId() {
        return trackId;
    }

    public void setTrackId(long trackId) {
        this.trackId = trackId;
    }

    public BigDecimal getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(BigDecimal unitPrice) {
        this.unitPrice = unitPrice;
    }

    public long getQuantity() {
        return quantity;
    }

    public void setQuantity(long quantity) {
        this.quantity = quantity;
    }

    public static List<InvoiceItem> getForInvoice(long invoiceId) {
        try (Connection conn = DB.connect();
             PreparedStatement stmt = conn.prepareStatement(
                     "SELECT * FROM invoice_items WHERE InvoiceId=? LIMIT ? OFFSET ?")) {
            stmt.setLong(1, invoiceId);
            stmt.setInt(2, Web.PAGE_SIZE);
            stmt.setInt(3, (Web.getPage() - 1) * 10);
            ResultSet results = stmt.executeQuery();
            List<InvoiceItem> resultList = new LinkedList<>();
            while (results.next()) {
                resultList.add(new InvoiceItem(results));
            }
            return resultList;
        } catch (SQLException sqlException) {
            throw new RuntimeException(sqlException);
        }
    }
}
