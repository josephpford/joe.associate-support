const url = `${window.API_URL}/api/board-game`;

export const findAll = async () => {
  const response = await fetch(url);
  if (response.status === 200) {
    return response.json();
  } else {
    Promise.reject(['Something unexpected happened!']);
  }
}

export const findById = async (id) => {
  const response = await fetch(`${url}/${id}`);
  if (response.status === 200) {
    return response.json();
  } else if (response.status === 404) {
    return ['Board game not found.']
  } else {
    Promise.reject(['Something unexpected happened!']);
  }
}

export const add = async (game) => {

  const token = getToken();
  if (!token) {
    return Promise.reject("Forbidden!")
  }

  const init = {
    method: 'POST',
    headers: {
      'Content-Type': 'application/json',
      'Accept': 'application/json',
      'Authorization': `Bearer ${token}`
    },
    body: JSON.stringify(game)
  };

  const response = await fetch(url, init);
  if (response.status === 201 || response.status === 400) {
    return response.json();
  } else {
    Promise.reject(['Something unexpected happened!']);
  }
}

export const update = async (game) => {

  const token = getToken();
  if (!token) {
    return Promise.reject("Forbidden!")
  }

  const init = {
    method: 'PUT',
    headers: {
      'Content-Type': 'application/json',
      'Accept': 'application/json',
      'Authorization': `Bearer ${token}`
    },
    body: JSON.stringify(game)
  };

  const response = await fetch(`${url}/${game.id}`, init);
  if (response.status === 204) {
    return null;
  } else if (response.status === 400) {
    return response.json();
  } else if (response.status === 404) {
    return ['Game not found.'];
  } else {
    Promise.reject(['Something unexpected happened!']);
  }
}

export const deleteById = async (id) => {

  const token = getToken();
  if (!token) {
    return Promise.reject("Forbidden!")
  }

  const init = {
    method: 'DELETE',
    headers: {
      'Authorization': `Bearer ${token}`
    }
  };

  const response = await fetch(`${url}/${id}`, init);
  if (response.status === 204) {
    return null;
  } else if (response.status === 404) {
    return ['Game not found.'];
  } else {
    Promise.reject(['Something unexpected happened!']);
  }
}

const getToken = () => {
  return localStorage.getItem('jwt_token');
}