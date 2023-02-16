const url = `${window.API_URL}/api/category`;

export const findAll = async () => {
  const response = await fetch(url);
  if (response.status === 200) {
    return response.json();
  } else {
    Promise.reject(['Something unexpected happened!']);
  }
}