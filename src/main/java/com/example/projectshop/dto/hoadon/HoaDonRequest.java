package com.example.projectshop.dto.hoadon;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
public class HoaDonRequest {
    private Integer id;
    private String maHoaDon;
    private String tenKhachHang;
    private String soDienThoai;
    private String diaChi;
    private String phuongXa;
    private String quanHuyen;
    private String tinhThanh;
    private String tongTien;
    private String tienGiam;
    private String tongTienSauGiam;
    private String phiVanChuyen;
    private Integer phuongThucThanhToan;
    private String bankCode;
    private Integer trangThai;
    private Integer phieuGiamGia;
    private Integer khachHang;
    private Integer nhanVien;
    private List<HoaDonChiTietRequest> hoaDonChiTietRequests;
}
