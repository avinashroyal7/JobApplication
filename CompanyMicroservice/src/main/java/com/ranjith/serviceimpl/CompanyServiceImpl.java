package com.ranjith.serviceimpl;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.ranjith.clients.ReviewClients;
import com.ranjith.dto.ReviewMessage;
import com.ranjith.entity.Company;
import com.ranjith.repository.CompanyRepository;
import com.ranjith.service.CompanyService;

import jakarta.ws.rs.NotFoundException;

@Service
public class CompanyServiceImpl implements CompanyService {

	private CompanyRepository companyRepository;
	
	private ReviewClients reviewClients;
	public CompanyServiceImpl(CompanyRepository companyRepository,ReviewClients reviewClients) {
		this.companyRepository = companyRepository;
		this.reviewClients = reviewClients;
	}

	@Override
	public List<Company> getAllCompanies() {
		return companyRepository.findAll();
	}

	@Override
	public boolean updateCompany(Company company, Long id) {
		Optional<Company> companyOptional = companyRepository.findById(id);
		if(companyOptional.isPresent()) {
			Company companyToUpdate = companyOptional.get();
			companyToUpdate.setDescription(company.getDescription());
			companyToUpdate.setName(company.getName());
			companyRepository.save(companyToUpdate);
			return true;
		}
		return false;
	}

	@Override
	public void createCompany(Company company) {
		companyRepository.save(company);
	}

	@Override
	public boolean deleteComapanyById(Long id) {
		if(companyRepository.existsById(id)) {
			companyRepository.deleteById(id);
			return true;
		}
		return false;
	}

	@Override
	public Company getCompanyById(Long id) {
		return companyRepository.findById(id).orElse(null);
	}

	@Override
	public void updateCompanyRating(ReviewMessage reviewMessage) {
		Company company = companyRepository.findById(reviewMessage.getCompanyId())
				.orElseThrow(()->new NotFoundException("Company Not Found"+reviewMessage.getCompanyId()));
		//System.out.println(reviewMessage.getDescription());
		
		double averageRating=reviewClients.getAverageRatingForCompany(reviewMessage.getCompanyId());
		company.setRating(averageRating);
		companyRepository.save(company);
	}

}
