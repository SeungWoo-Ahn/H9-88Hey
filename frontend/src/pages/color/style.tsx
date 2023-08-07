import { css } from '@emotion/react';
import styled from '@emotion/styled';

export const Container = styled.div`
  width: 331px;
  gap: 35px;

  display: flex;
  align-items: center;
`;

export const Wrapper = styled.div`
  display: flex;
  flex-direction: column;
`;

export const Box = styled.div``;

export const TitleBox = styled.div`
  display: flex;
  align-items: flex-end;
  justify-content: space-between;
`;

export const Title = styled.span`
  ${({ theme }) => {
    const { colors, fonts } = theme;
    return css`
      ${fonts.headingMedium3}
      color: ${colors.black}
    `;
  }}
`;

export const ColorName = styled.span`
  ${({ theme }) => {
    const { colors, fonts } = theme;
    return css`
      ${fonts.bodyRegular4}
      color: ${colors.black}
    `;
  }}
`;

export const Division = styled.div`
  ${({ theme }) => {
    const { colors } = theme;
    return css`
      width: 100%;
      height: 1px;
      margin-top: 9px;
      margin-bottom: 18px;

      background-color: ${colors.hyundaiSand};
    `;
  }}
`;

export const ColorBox = styled.div`
  gap: 10px;

  display: grid;
  grid-template-columns: repeat(3, 1fr);
`;

export const ColorCard = styled.div`
  gap: 8px;

  display: flex;
  flex-direction: column;
  align-items: center;
  position: relative;

  cursor: pointer;
`;

interface ColorCardRectProps {
  isActive: boolean;
  colorValue: string;
}

export const ColorCardRect = styled.div<ColorCardRectProps>`
  ${({ isActive, colorValue }) => {
    return css`
      width: 89px;
      height: 89px;

      background-color: ${colorValue};
      border: ${isActive ? '3px solid #00aad2' : 'none'};
      border-radius: 8px;
    `;
  }}
`;

export const ColorCardName = styled.span`
  ${({ theme }) => {
    const { fonts, colors } = theme;
    return css`
      ${fonts.bodyRegular4}
      color: ${colors.black}
    `;
  }}
`;

export const InteriorColorBox = styled.div`
  gap: 16px;

  display: grid;
  grid-template-rows: repeat(1, 1fr);
`;

export const InteriorColorCard = styled.div`
  position: relative;
`;

interface InteriorColorButtonProps {
  isActive: boolean;
  bgImage: string;
}

export const InteriorColorButton = styled.button<InteriorColorButtonProps>`
  ${({ isActive, bgImage }) => {
    return css`
      width: 331px;
      height: 68px;

      background-image: url(${bgImage});
      background-repeat: no-repeat;
      background-size: cover;
      background-color: transparent;
      border-radius: 4px;
      border: 3px solid ${isActive ? '#00aad2' : '#fff'};
    `;
  }}
`;
