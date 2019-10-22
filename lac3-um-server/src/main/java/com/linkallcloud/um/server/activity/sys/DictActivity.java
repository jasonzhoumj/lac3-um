package com.linkallcloud.um.server.activity.sys;

import com.linkallcloud.core.activity.BaseTreeActivity;
import com.linkallcloud.core.dto.Trace;
import com.linkallcloud.core.exception.BaseRuntimeException;
import com.linkallcloud.core.exception.Exceptions;
import com.linkallcloud.core.lang.Strings;
import com.linkallcloud.um.activity.sys.IDictActivity;
import com.linkallcloud.um.domain.sys.Dict;
import com.linkallcloud.um.domain.sys.DictType;
import com.linkallcloud.um.server.dao.sys.IDictDao;
import com.linkallcloud.um.server.dao.sys.IDictTypeDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class DictActivity extends BaseTreeActivity<Dict, IDictDao> implements IDictActivity {
    @Autowired
    private IDictDao dictDao;

    @Autowired
    private IDictTypeDao dictTypeDao;

    @Override
    public IDictDao dao() {
        return dictDao;
    }

    @Override
    public List<Dict> findDictsByDictTypeId(Trace t, Long dictTypeId) {
        return dao().getDictsByTypeId(t, dictTypeId);
    }

    @Override
    public List<Dict> findDictsByDictTopTypeId(Trace t, Long topDictTypeId) {
        return dao().getDictsByTopTypeId(t, topDictTypeId);
    }

    @Override
    protected void treeBefore(Trace t, boolean isNew, Dict entity) {
        if (isNew) {
            if (entity.getParentId() == null || entity.getParentId() <= 0L) {
                throw new BaseRuntimeException(Exceptions.CODE_ERROR_PARAMETER, "parentId参数错误。");
            }

            if (!Strings.isBlank(entity.getGovCode())) {
                Dict dbEntity = dao().fetchByGovCode(t, entity.getGovCode());
                if (dbEntity != null) {
                    throw new BaseRuntimeException(Exceptions.CODE_ERROR_PARAMETER,
                            "已经存在相同govCode的节点：" + entity.getGovCode());
                }
            }

            DictType parent = dictTypeDao.fetchById(t, entity.getParentId());
            Long topParentId = Long.valueOf(parent.getCode().toString().split(parent.codeTag())[0]);
            entity.setTopTypeId(topParentId);
        }
    }

    @Override
    protected void treeAfter(Trace t, boolean isNew, Dict entity) {

    }

}
