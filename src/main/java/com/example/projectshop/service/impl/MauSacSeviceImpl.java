package com.example.projectshop.service.impl;

import com.example.projectshop.domain.MauSac;
import com.example.projectshop.dto.mausac.MauSacRequest;
import com.example.projectshop.dto.mausac.MauSacResponse;
import com.example.projectshop.repository.ChiTietSanPhamRepository;
import com.example.projectshop.repository.HoaDonChiTietRepository;
import com.example.projectshop.repository.MauSacRepository;
import com.example.projectshop.service.IMauSacService;
import com.example.projectshop.service.ObjectMapperUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MauSacSeviceImpl implements IMauSacService {

    @Autowired
    private MauSacRepository mauSacRepository;

    @Autowired
    private ChiTietSanPhamRepository chiTietSanPhamRepo;

    @Override
    public List<MauSacResponse> getAll() {
        List<MauSacResponse> list= ObjectMapperUtils.mapAll(mauSacRepository.findAll(),MauSacResponse.class);
        return list;
    }

    @Override
    public MauSacResponse findById(Integer id) {
        return ObjectMapperUtils.map(mauSacRepository.findById(id).get(), MauSacResponse.class);
    }

    @Override
    public MauSacResponse create(MauSacRequest mauSacRequest) {
        MauSac entity = ObjectMapperUtils.map(mauSacRequest, MauSac.class);
        entity.setTen(mauSacRequest.getTen());
        entity = mauSacRepository.save(entity);
        MauSacResponse response = ObjectMapperUtils.map(entity, MauSacResponse.class);
        return response;    }

    @Override
    public MauSacResponse update(MauSacRequest mauSacRequest, Integer id) {
        MauSac eDb = mauSacRepository.findById(id).get();
        MauSac entity = ObjectMapperUtils.map(mauSacRequest, MauSac.class);
        entity.setId(eDb.getId());
        entity.setTen(mauSacRequest.getTen());
        entity = mauSacRepository.save(entity);
        return ObjectMapperUtils.map(mauSacRepository.save(entity), MauSacResponse.class);
    }

  @Override
    public void delete(Integer id) {
        MauSacRequest mauSacRequest = new MauSacRequest();
      mauSacRequest.setId(id);
      mauSacRequest.setTen(this.findById(id).getTen());
      mauSacRequest.setTrangThai(2);
        this.update(mauSacRequest,id);
    }

    @Override
    public Page<MauSacResponse> findAllMauSac(String pageParam, String limitParam) {
        Integer page = pageParam == null ? 0 : Integer.valueOf(pageParam);
        Integer limit = limitParam == null ? 3 : Integer.valueOf(limitParam);
        Pageable pageable = PageRequest.of(page, limit);
        Page<MauSacResponse> list = ObjectMapperUtils.mapEntityPageIntoDtoPage(mauSacRepository.findAll(pageable), MauSacResponse.class);
        return list;
    }


}
