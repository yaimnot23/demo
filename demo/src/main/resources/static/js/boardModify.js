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
});

document.getElementById("subBtn").addEventListener("click", () => {
  document.getElementById("modForm").submit();
});

document.getElementById("delBtn").addEventListener("click", () => {
  document.getElementById("modForm").action = "/board/remove";
  document.getElementById("modForm").submit();
});
