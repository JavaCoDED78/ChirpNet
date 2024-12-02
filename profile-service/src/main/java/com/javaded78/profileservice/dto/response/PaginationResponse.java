package com.javaded78.profileservice.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.domain.Page;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PaginationResponse<T> {

	List<T> content;
	MetaData metaData;

	public static <T> PaginationResponse<T> of(Page<T> page) {
		MetaData metaData = new MetaData(page.getNumber(), page.getSize(), page.getTotalElements());
		return new PaginationResponse<>(page.getContent(), metaData);
	}

	public record MetaData(int page, int size, long totalElements) {
	}
}
