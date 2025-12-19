# 삭제 기능 구현 계획

사용자가 삭제 기능을 요청했습니다. `BoardVO`에 `is_del` 컬럼이 있고 `getList`에서 이를 필터링(`is_del = 'N'`)하고 있으므로, **Soft Delete** 방식으로 구현합니다.

## 변경 제안

### Frontend

#### [수정] `boardModify.js`

- `#delBtn`에 클릭 이벤트 리스너를 추가합니다.
- 클릭 시 `modForm`의 action을 `/board/remove`로 변경하고 submit 합니다.

### Backend

#### [수정] `BoardController.java`

- `@PostMapping("/remove")` 메서드를 추가합니다.
- `boardService.remove(bno)`를 호출합니다.

#### [수정] `BoardService.java` / `BoardServiceImpl.java`

- 인터페이스에 `int remove(int bno)`를 추가합니다.
- 구현체에서 `boardDAO.delete(bno)`를 호출하도록 구현합니다.

#### [수정] `BoardDAO.java`

- `int delete(int bno)` 메서드를 추가합니다.

#### [수정] `boardMapper.xml`

- `<update id="delete">` 태그를 추가하여 `is_del = 'Y'`로 업데이트하는 쿼리를 작성합니다.
