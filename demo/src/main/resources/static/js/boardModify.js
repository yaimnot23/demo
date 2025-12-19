console.log("boardModify.js in");

document.getElementById("modBtn").addEventListener("click", () => {
  // 1. Title, Content의 readonly 해제
  document.getElementById("title").readOnly = false;
  document.getElementById("content").readOnly = false;

  // 2. Title에 포커스 주기 (바로 입력할 수 있게)
  document.getElementById("title").focus();

  // 3. 버튼 교체 (Modify/Delete 숨기기, Submit 보이기)
  document.getElementById("modBtn").style.display = "none";
  document.getElementById("delBtn").style.display = "none";
  document.getElementById("subBtn").style.display = "inline-block";

  // 4. 파일 추가 영역 보이기
  let fileDiv = document.getElementById("fileInputDiv");
  if (fileDiv) {
    fileDiv.style.display = "block";
  }

  // 5. 파일 삭제 버튼(X) 보이기
  let delBtns = document.querySelectorAll(".file-del-btn");
  for (let btn of delBtns) {
    btn.style.display = "inline-block";
  }
});

// 파일 삭제 버튼 클릭 이벤트 위임 or 직접 등록
document.addEventListener("click", (e) => {
  if (e.target.classList.contains("file-del-btn")) {
    let uuid = e.target.getAttribute("data-uuid");
    console.log("deleting " + uuid);
    // input hidden 생성
    let input = document.createElement("input");
    input.type = "hidden";
    input.name = "removeFiles";
    input.value = uuid;
    document.getElementById("modForm").appendChild(input);

    // 화면에서 제거 (부모 div 숨기기)
    // .d-flex가 부모
    let parent = e.target.closest(".d-flex");
    if (parent) {
      parent.remove();
    }
  }
});

document.getElementById("subBtn").addEventListener("click", () => {
  document.getElementById("modForm").submit();
});

document.getElementById("delBtn").addEventListener("click", () => {
  document.getElementById("modForm").action = "/board/remove";
  document.getElementById("modForm").submit();
});
