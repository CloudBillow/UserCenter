package com.miracle.usercenter.pojo.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Token
 *
 * @author XieYT
 * @since 2023/03/01 13:35
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TokenVO {

    /**
     * Token
     */
    private String accessToken;

    /**
     * 刷新Token
     */
    private String refreshToken;
}
