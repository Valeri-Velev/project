package com.example.demo.viewModel;

    public class ImageViewModel {
        private String body;
        private Integer id;

        public ImageViewModel(String body, Integer id) {
            this.body = body;
            this.id = id;
        }

        public ImageViewModel(){

        }

        public String getBody() {
            return body;
        }

        public void setBody(String body) {
            this.body = body;
        }

        public Integer getId() {
            return id;
        }

        public void setId(Integer id) {
            this.id = id;
        }
    }
