import { SelectOptionImageBox } from '@components/common/SelectOptionImageBox';
import { SelectOptionDescription } from '@components/common/SelectOptionDescription';
import { SelectOptionDetail } from '@components/common/SelectOptionDetail';

export function TrimTemplate() {
  return (
    <>
      <SelectOptionImageBox image="src/assets/hmg.png" />
      <SelectOptionDescription
        title="컴포트 ||"
        price="690,000"
        tags={['여름에 쓰기 좋아요☀️', '옵션값 뽑았어요👍', '편리해요☺️']}
      />
      <SelectOptionDetail title="헤드업 디스플레이" />
    </>
  );
}
