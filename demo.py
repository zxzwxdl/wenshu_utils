import json
from pprint import pprint

import requests

from wenshu_utils.docid.decrypt import decrypt_doc_id
from wenshu_utils.docid.runeval import decrypt_runeval
from wenshu_utils.document.parse import parse_detail
from wenshu_utils.vl5x.args import Vjkl5, Vl5x, Number, Guid
from wenshu_utils.wzws.decrypt import decrypt_wzws


class Demo:
    def __init__(self):
        self.session = requests.Session()
        self.session.headers.update({
            "User-Agent": "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_14_5) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/75.0.3770.100 Safari/537.36",
        })

        self.error_msg = "请开启JavaScript并刷新该页"

    def list_page(self):
        """文书列表页"""
        html_url = "http://wenshu.court.gov.cn/list/list"
        response = self.session.get(html_url)
        text = response.content.decode()

        if self.error_msg in text:
            retry = 3
            for _ in range(retry):
                redirect_url = decrypt_wzws(text)
                response = self.session.get(redirect_url)
                text = response.content.decode()
                if self.error_msg not in text:
                    break
            else:
                raise Exception("连续{}次获取wzws_cid失败".format(retry))

        #
        ajax_url = "http://wenshu.court.gov.cn/List/ListContent"
        data = {
            "Param": "案件类型:刑事案件",
            "Index": 1,
            "Page": 10,
            "Order": "法院层级",
            "Direction": "asc",
            "vl5x": Vl5x(self.session.cookies.setdefault("vjkl5", Vjkl5())),
            "number": Number(),
            "guid": Guid(),
        }
        response = self.session.post(ajax_url, data=data)

        json_data = json.loads(response.json())
        print("列表数据:", json_data)

        run_eval = json_data.pop(0)["RunEval"]
        try:
            key = decrypt_runeval(run_eval)
        except ValueError as e:
            raise ValueError("返回脏数据") from e
        else:
            print("RunEval解析完成:", key, "\n")

        key = key.encode()
        for item in json_data:
            cipher_text = item["文书ID"]
            print("解密:", cipher_text)
            plain_text = decrypt_doc_id(doc_id=cipher_text, key=key)
            print("成功, 文书ID:", plain_text, "\n")

    def detail_page(self):
        """文书详情页"""
        url = "http://wenshu.court.gov.cn/CreateContentJS/CreateContentJS.aspx"
        params = {
            "DocID": "029bb843-b458-4d1c-8928-fe80da403cfe",
        }
        response = self.session.get(url, params=params)
        text = response.content.decode()

        if self.error_msg in text:
            retry = 3
            for _ in range(retry):
                redirect_url = decrypt_wzws(text)
                response = self.session.get(redirect_url)
                text = response.content.decode()
                if self.error_msg not in text:
                    break
            else:
                raise Exception("连续{}次获取wzws_cid失败".format(retry))

        group_dict = parse_detail(response.text)
        pprint(group_dict)


if __name__ == '__main__':
    demo = Demo()
    demo.list_page()
    demo.detail_page()
