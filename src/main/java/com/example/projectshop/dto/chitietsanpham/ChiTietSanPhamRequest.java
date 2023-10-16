package com.example.projectshop.dto.chitietsanpham;

import com.example.projectshop.domain.AnhSanPham;
import com.example.projectshop.domain.ChatLieuDeGiay;
import com.example.projectshop.domain.ChatLieuGiay;
import com.example.projectshop.domain.Kichco;
import com.example.projectshop.domain.Mausac;
import com.example.projectshop.domain.SanPham;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.sql.Date;
import java.util.List;

@Getter
@Setter
public class ChiTietSanPhamRequest {
    private Integer id;

    private Integer soLuong;

    private BigDecimal giaBan;

    private Date ngayTao;

    private Integer trangThai;

    private Mausac mausac;

    private Kichco kichco;

    private ChatLieuGiay chatLieuGiay;

    private ChatLieuDeGiay chatLieuDeGiay;

    private SanPham sanpham;

    private List<AnhSanPham> list;
}