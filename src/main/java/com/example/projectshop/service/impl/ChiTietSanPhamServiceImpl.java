package com.example.projectshop.service.impl;

import com.example.projectshop.domain.AnhSanPham;
import com.example.projectshop.domain.ChiTietSanPham;
import com.example.projectshop.domain.SanPham;
import com.example.projectshop.dto.chitietsanpham.ChiTietSanPhamRequest;
import com.example.projectshop.dto.chitietsanpham.ChiTietSanPhamResponse;
import com.example.projectshop.repository.AnhSanPhamRepository;
import com.example.projectshop.repository.ChiTietSanPhamRepository;
import com.example.projectshop.repository.HoaDonChiTietRepository;
import com.example.projectshop.service.CloudinaryService;
import com.example.projectshop.service.IChiTietSanPhamService;
import com.example.projectshop.service.ObjectMapperUtils;
import com.example.projectshop.utils.URLDecode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ChiTietSanPhamServiceImpl implements IChiTietSanPhamService {

    @Autowired
    private ChiTietSanPhamRepository chiTietSanPhamRepo;

    @Autowired
    private AnhSanPhamRepository anhSanPhamRepo;

    @Autowired
    private CloudinaryService cloudinaryService;


    @Override
    public Page<ChiTietSanPham> findAll(String priceMin,
                                        String priceMax,
                                        String color,
                                        String shoe_material,
                                        String shoe_sole_material,
                                        Integer page,
                                        Integer pageSize) {

        Pageable pageable = PageRequest.of(page > 0 ? page - 1 : page, pageSize);

        BigDecimal priceMinOutput = priceMin == null ? BigDecimal.valueOf(0) : BigDecimal.valueOf(Long.valueOf(priceMin));
        BigDecimal priceMaxOutput = priceMax == null ? chiTietSanPhamRepo.getTop1ByPriceMax().getGiaBan() : BigDecimal.valueOf(Long.valueOf(priceMax));

        List<Integer> listMauSac = color == null ? null : Arrays.stream(URLDecode.getDecode(color).split(","))
                .map(Integer::valueOf)
                .collect(Collectors.toList());

        List<Integer> listChatLieuGiay = shoe_material == null ? null : Arrays.stream(URLDecode.getDecode(shoe_material).split(","))
                .map(Integer::valueOf)
                .collect(Collectors.toList());

        List<Integer> listChatLieuDeGiay = shoe_sole_material == null ? null : Arrays.stream(URLDecode.getDecode(shoe_sole_material).split(","))
                .map(Integer::valueOf)
                .collect(Collectors.toList());

        return chiTietSanPhamRepo.getAllByParam(
                priceMinOutput,
                priceMaxOutput,
                listMauSac,
                listChatLieuGiay,
                listChatLieuDeGiay,
                pageable);
    }


    @Override
    public Optional<ChiTietSanPham> findById(Integer id) {
        return chiTietSanPhamRepo.findById(id);
    }

    @Override
    public ChiTietSanPham create(ChiTietSanPhamRequest chiTietSanPhamRequest) {
        ChiTietSanPham chiTietSanPham = ObjectMapperUtils.map(chiTietSanPhamRequest,ChiTietSanPham.class);
        chiTietSanPham.setId(null);
        for (Map map: cloudinaryService.uploadListFile(chiTietSanPhamRequest.getAnhSanPham())){
            AnhSanPham anhSanPham = new AnhSanPham();
            anhSanPham.setId(String.valueOf(map.get("public_id")));
            anhSanPham.setTen(String.valueOf(map.get("url")));
            anhSanPham.setChiTietSanPham(chiTietSanPham);
            anhSanPhamRepo.save(anhSanPham);
        }
        return chiTietSanPhamRepo.save(chiTietSanPham);
    }

    @Override
    public ChiTietSanPham update(Integer id, ChiTietSanPhamRequest chiTietSanPhamRequest) {
        if (chiTietSanPhamRequest.getAnhSanPham()==null){
            ChiTietSanPham chiTietSanPham = ObjectMapperUtils.map(chiTietSanPhamRequest, ChiTietSanPham.class);
            chiTietSanPham.setId(id);
            return chiTietSanPhamRepo.save(chiTietSanPham);
        }
        ChiTietSanPham chiTietSanPham2 = ObjectMapperUtils.map(chiTietSanPhamRequest,ChiTietSanPham.class);
        chiTietSanPham2.setId(id);
        for (Map map: cloudinaryService.uploadListFile(chiTietSanPhamRequest.getAnhSanPham())){
            AnhSanPham anhSanPham = new AnhSanPham();
            anhSanPham.setId(String.valueOf(map.get("public_id")));
            anhSanPham.setTen(String.valueOf(map.get("url")));
            anhSanPham.setChiTietSanPham(chiTietSanPham2);
            anhSanPhamRepo.save(anhSanPham);
        }
        return chiTietSanPhamRepo.save(chiTietSanPham2);
    }

    @Override
    public ChiTietSanPham delete(Integer id) {
        Optional<ChiTietSanPham> chiTietSanPham = this.findById(id);
        if (chiTietSanPham.isPresent()){
            chiTietSanPham.get().setTrangThai(0);
            return chiTietSanPhamRepo.save(chiTietSanPham.get());
        }
        return null;
    }

    @Override
    public Page<ChiTietSanPham> search(String keyword, Integer page, Integer pageSize) {
        Pageable pageable = PageRequest.of(page > 0 ? page - 1 : page, pageSize);
        return chiTietSanPhamRepo.search(keyword,pageable);
    }
}
