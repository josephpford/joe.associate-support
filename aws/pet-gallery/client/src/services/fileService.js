const url = `${process.env.REACT_APP_API_URL}/api/file`;

export async function uploadFile(formData) {
  const init = {
    method: 'POST',
    body: formData
  };  
  const response = await fetch(url, init);
  if (response.status === 400 || response.status === 409) {
    const errors = await response.json();
    return Promise.reject(errors);
  } else if (response.status === 201) {
    return response.text();
  }
  return Promise.reject(['Unable to upload image.']);
}